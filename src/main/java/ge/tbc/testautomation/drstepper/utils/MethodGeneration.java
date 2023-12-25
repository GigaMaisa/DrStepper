package ge.tbc.testautomation.drstepper.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import ge.tbc.testautomation.drstepper.actions.Action;

import javax.lang.model.element.Modifier;

public final class MethodGeneration {
    private static MethodSpec generateIsVisibleMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "IsVisible")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.shouldBe($T.visible)", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateIsNotVisibleMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "IsNotVisible")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.shouldNotBe($T.visible)", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateIsEnabledMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "IsEnabled")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.shouldBe($T.enabled)", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateNotEnabledMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "IsNotEnabled")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.shouldNotBe($T.enabled)", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateHasTextMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "HasText")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(String.class, "text")
                .addStatement("elements.$L.shouldHave($T.text(text))", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateHasAttributeMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "HasAttribute")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(String.class, "attrName")
                .addParameter(String.class, "attrValue")
                .addStatement("elements.$L.shouldHave($T.attribute(attrName, attrValue))", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateHasCssValueMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("validate" + capitalize(fieldName) + "HasCssValue")
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(String.class, "propertyName")
                .addParameter(String.class, "expectedPropertyValue")
                .addStatement("elements.$L.shouldHave($T.cssValue(propertyName, expectedPropertyValue))", fieldName, com.codeborne.selenide.Condition.class)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateCheckMethod(ClassName self, String fieldName) {
        return MethodSpec.methodBuilder("check" + capitalize(fieldName))
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(boolean.class, "isChecked")
                .addStatement("elements.$L.setSelected(isChecked)", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateHoverMethod(ClassName self, String fieldName) {
        String methodName = "hoverOver" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.hover()", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateClickMethod(ClassName self, String fieldName) {
        String methodName = "click" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.click()", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generatePressEnterMethod(ClassName self, String fieldName) {
        String methodName = "pressEnterOn" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addStatement("elements.$L.pressEnter()", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateScrollMethod(ClassName self, String fieldName) {
        String methodName = "scrollTo" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(boolean.class, "scrollType")
                .addStatement("elements.$L.scrollIntoView(scrollType)", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateSelectMethod(ClassName self, String fieldName) {
        String methodName = "selectOptionFrom" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(String.class, "option")
                .addStatement("elements.$L.selectOption(option)", fieldName)
                .addStatement("return this")
                .build();
    }

    private static MethodSpec generateFillMethod(ClassName self, String fieldName) {
        String methodName = "fill" + capitalize(fieldName);
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(self)
                .addParameter(String.class, "text")
                .addStatement("elements.$L.setValue(text)", fieldName)
                .addStatement("return this")
                .build();
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static MethodSpec generateMethodForAction(ClassName self, Action action, String fieldName) {
        switch (action) {
            case SCROLL:
                return generateScrollMethod(self, fieldName);

            case SELECT:
                return generateSelectMethod(self, fieldName);

            case FILL:
                return generateFillMethod(self, fieldName);

            case CLICK:
                return generateClickMethod(self, fieldName);

            case IS_VISIBLE:
                return generateIsVisibleMethod(self, fieldName);

            case NOT_VISIBLE:
                return generateIsNotVisibleMethod(self, fieldName);

            case ENABLED:
                return generateIsEnabledMethod(self, fieldName);

            case NOT_ENABLED:
                return generateNotEnabledMethod(self, fieldName);

            case HAS_TEXT:
                return generateHasTextMethod(self, fieldName);

            case CHECK:
                return generateCheckMethod(self, fieldName);

            case HOVER:
                return generateHoverMethod(self, fieldName);

            case HAS_ATTRIBUTE:
                return generateHasAttributeMethod(self, fieldName);

            case ENTER:
                return generatePressEnterMethod(self, fieldName);

            case HAS_CSS_VALUE:
                return generateHasCssValueMethod(self, fieldName);

            default:
                return null;
        }
    }
}
