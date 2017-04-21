//package util;
//
//import org.junit.Test;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import java.lang.reflect.Field;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.mock;
//
//public class ReflectionUtilsTest {
//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.FIELD)
//    @interface TestAnnotation {}
//
//    @Test(expected = NullPointerException.class)
//    public void hasDeclaredAnnotationNullField() throws Exception {
//        ReflectionUtils.hasDeclaredAnnotation(null, TestAnnotation.class);
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void hasDeclaredAnnotationNullAnnotation() throws Exception {
//        class Tested {
//            @TestAnnotation
//            int annotatedField;
//        }
//
//        Field annotatedField = Tested.class.getDeclaredField("annotatedField");
//        ReflectionUtils.hasDeclaredAnnotation(annotatedField, null);
//    }
//
//    @Test
//    public void hasDeclaredAnnotationTrue() throws Exception {
//        class Tested {
//            @TestAnnotation
//            int annotatedField;
//        }
//
//        Field annotatedField = Tested.class.getDeclaredField("annotatedField");
//
//        assertTrue(ReflectionUtils.hasDeclaredAnnotation(annotatedField, TestAnnotation.class));
//    }
//
//    @Test
//    public void hasDeclaredAnnotationFalse() throws Exception {
//        class Tested {
//            int unAnnotatedField;
//        }
//
//        Field unAnnotatedField = Tested.class.getDeclaredField("unAnnotatedField");
//
//        assertFalse(ReflectionUtils.hasDeclaredAnnotation(unAnnotatedField, TestAnnotation.class));
//    }
//
//}