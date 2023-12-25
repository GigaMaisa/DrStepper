package ge.tbc.testautomation.drstepper.core;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import ge.tbc.testautomation.drstepper.actions.Action;
import ge.tbc.testautomation.drstepper.actions.Actions;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static ge.tbc.testautomation.drstepper.utils.MethodGeneration.generateMethodForAction;

public class DrStepper {
    public static void generateStepClassesWithPageClasses(Class<?>... pageClasses) {
        for (Class<?> clazz : pageClasses) {
            generateStepClassWithPageClass(clazz);
        }
    }

    private static void generateStepClassWithPageClass(Class<?> clazz) {
        String pageClassName = clazz.getSimpleName();
        String className = pageClassName + "Steps";
        String pageClassPackage = "page";
        String stepsClassPackage = "steps";

        ClassName self = ClassName.get(stepsClassPackage, className);
        ClassName pageClass = ClassName.get(pageClassPackage, pageClassName);

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(pageClass, "elements", Modifier.PRIVATE, Modifier.FINAL)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.elements = new $T()", pageClass)
                        .build());

        Map<String, Boolean> methodGenerated = new HashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            Actions actionsAnnotation = field.getAnnotation(Actions.class);

            if (actionsAnnotation != null) {
                for (Action action : actionsAnnotation.value()) {
                    MethodSpec method = generateMethodForAction(self, action, field.getName());

                    if (method != null && methodGenerated.putIfAbsent(method.name, true) == null) {
                        classBuilder.addMethod(method);
                    }
                }
            } else {
                for (Action action : Action.values()) {
                    MethodSpec method = generateMethodForAction(self, action, field.getName());
                    if (method != null && methodGenerated.putIfAbsent(method.name, true) == null) {
                        classBuilder.addMethod(method);
                    }
                }
            }
        }
        
        try {
            JavaFile javaFile = JavaFile.builder(stepsClassPackage, classBuilder.build()).build();
            javaFile.writeTo(Paths.get("src/main/java"));
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
