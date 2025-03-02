package com.example.gpaCalculator.model;

/**
 * Module class that represents one of the modules with simplified attributes.
 */
public class Module {

    // Essential attributes from the JSON file
    private String id_module;       // Unique identifier for the module
    private String Nom_module;      // Name of the module
    private boolean hasCours;       // Indicates if the module has a Cours component
    private boolean hasTD;          // Indicates if the module has a TD (Tutorial) component
    private boolean hasTP;          // Indicates if the module has a TP (Practical) component
    private float Coefficient;      // Coefficient of the module (used in GPA calculation)
    private float Credit;           // Credit value of the module (used in GPA calculation)
    private String unite;           // Unit of the module

    // Note values (initialized to 0)
    private float coursNote;        // Note for Cours
    private float tdNote;           // Note for TD (Tutorial)
    private float tpNote;           // Note for TP (Practical)

    // Default constructor (useful for serialization/deserialization)
    public Module() {}

    // Parameterized constructor
    public Module(String id_module, String Nom_module, String cours, String td, String tp,
                  String Coefficient, String Credit, String unite) {
        this.id_module = id_module;
        this.Nom_module = Nom_module;

        // Parse boolean flags from strings
        this.hasCours = Boolean.parseBoolean(cours);
        this.hasTD = Boolean.parseBoolean(td);
        this.hasTP = Boolean.parseBoolean(tp);

        // Parse Coefficient and Credit as floats
        this.Coefficient = Float.parseFloat(Coefficient);
        this.Credit = Float.parseFloat(Credit);

        this.unite = unite;

        // Initialize note values to 0 (default)
        this.coursNote = 0;
        this.tdNote = 0;
        this.tpNote = 0;
    }

    // Getters and Setters

    public String getId_module() {
        return id_module;
    }

    public void setId_module(String id_module) {
        this.id_module = id_module;
    }

    public String getNom_module() {
        return Nom_module;
    }

    public void setNom_module(String Nom_module) {
        this.Nom_module = Nom_module;
    }

    public boolean isHasCours() {
        return hasCours;
    }

    public void setHasCours(boolean hasCours) {
        this.hasCours = hasCours;
    }

    public boolean isHasTD() {
        return hasTD;
    }

    public void setHasTD(boolean hasTD) {
        this.hasTD = hasTD;
    }

    public boolean isHasTP() {
        return hasTP;
    }

    public void setHasTP(boolean hasTP) {
        this.hasTP = hasTP;
    }

    public float getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(float Coefficient) {
        this.Coefficient = Coefficient;
    }

    public float getCredit() {
        return Credit;
    }

    public void setCredit(float Credit) {
        this.Credit = Credit;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public float getCoursNote() {
        return coursNote;
    }

    public void setCoursNote(float coursNote) {
        this.coursNote = coursNote;
    }

    public float getTdNote() {
        return tdNote;
    }

    public void setTdNote(float tdNote) {
        this.tdNote = tdNote;
    }

    public float getTpNote() {
        return tpNote;
    }

    public void setTpNote(float tpNote) {
        this.tpNote = tpNote;
    }
}