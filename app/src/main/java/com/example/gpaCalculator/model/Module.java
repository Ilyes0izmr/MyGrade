package com.example.gpaCalculator.model;

/**
 * Module class that represents one of the modules with simplified attributes.
 */
import com.google.gson.annotations.SerializedName;

public class Module {
    @SerializedName("id_module")
    private String idModule;

    @SerializedName("Nom_module")
    private String nomModule;

    @SerializedName("cours")
    private boolean hasCours;

    @SerializedName("td")
    private boolean hasTD;

    @SerializedName("tp")
    private boolean hasTP;

    @SerializedName("Coefficient")
    private float coefficient;

    @SerializedName("Credit")
    private float credit;

    @SerializedName("unite")
    private String unite;

    // Note values (initialized to 0)
    private float coursNote;        // Note for Cours
    private float tdNote;           // Note for TD (Tutorial)
    private float tpNote;           // Note for TP (Practical)

    // Default constructor (useful for serialization/deserialization)
    public Module() {}

    // Parameterized constructor
    public Module(String id_module, String Nom_module, String cours, String td, String tp,
                  String Coefficient, String Credit, String unite) {
        this.idModule = id_module;
        this.nomModule = Nom_module;

        // Parse boolean flags from strings
        this.hasCours = Boolean.parseBoolean(cours);
        this.hasTD = Boolean.parseBoolean(td);
        this.hasTP = Boolean.parseBoolean(tp);

        // Parse Coefficient and Credit as floats
        this.coefficient = Float.parseFloat(Coefficient);
        this.credit = Float.parseFloat(Credit);

        this.unite = unite;

        // Initialize note values to 0 (default)
        this.coursNote = 0;
        this.tdNote = 0;
        this.tpNote = 0;
    }

    // Getters and Setters

    public String getId_module() {
        return idModule;
    }

    public void setId_module(String id_module) {
        this.idModule = id_module;
    }

    public String getNom_module() {
        return nomModule;
    }

    public void setNom_module(String Nom_module) {
        this.nomModule = Nom_module;
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
        return coefficient;
    }

    public void setCoefficient(float Coefficient) {
        this.coefficient = Coefficient;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float Credit) {
        this.credit = Credit;
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