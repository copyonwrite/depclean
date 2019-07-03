package se.kth.jdbl.pom.util;

public class ClassMembersVisitorCounter {

    // fields ---------------------------------------------------------------------------------------------------------

    private static int nbVisitedClasses;
    private static int nbVisitedFields;
    private static int nbVisitedMethods;
    private static int nbVisitedAnnotations;


    // public methods -------------------------------------------------------------------------------------------------

    public static void resetClassCounters() {
        nbVisitedClasses = 0;
        nbVisitedFields = 0;
        nbVisitedMethods = 0;
        nbVisitedAnnotations = 0;
    }

    public static void addVisitedClass() {
        nbVisitedClasses++;
    }

    public static void addVisitedField() {
        nbVisitedFields++;
    }

    public static void addVisitedMethod() {
        nbVisitedMethods++;
    }

    public static void addVisitedAnnotation() {
        nbVisitedAnnotations++;
    }

    // getters --------------------------------------------------------------------------------------------------------

    public static int getNbVisitedClasses() {
        return nbVisitedClasses;
    }

    public static int getNbVisitedFields() {
        return nbVisitedFields;
    }

    public static int getNbVisitedMethods() {
        return nbVisitedMethods;
    }

    public static int getNbVisitedAnnotations() {
        return nbVisitedAnnotations;
    }
}