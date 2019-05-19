package com.example.demo.temp;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;

import java.util.List;

public class GVAL {
    public static List<Cluster> ia;
    public static List<ClusterEdge> iae;
    public static List<Cluster> ny;
    public static List<ClusterEdge> nye;
    public static List<Cluster> oh;
    public static List<ClusterEdge> ohe;

    public static List<Cluster> getIa() {
        return ia;
    }

    public static void setIa(List<Cluster> ia) {
        GVAL.ia = ia;
    }

    public static List<ClusterEdge> getIae() {
        return iae;
    }

    public static void setIae(List<ClusterEdge> iae) {
        GVAL.iae = iae;
    }

    public static List<Cluster> getNy() {
        return ny;
    }

    public static void setNy(List<Cluster> ny) {
        GVAL.ny = ny;
    }

    public static List<ClusterEdge> getNye() {
        return nye;
    }

    public static void setNye(List<ClusterEdge> nye) {
        GVAL.nye = nye;
    }

    public static List<Cluster> getOh() {
        return oh;
    }

    public static void setOh(List<Cluster> oh) {
        GVAL.oh = oh;
    }

    public static List<ClusterEdge> getOhe() {
        return ohe;
    }

    public static void setOhe(List<ClusterEdge> ohe) {
        GVAL.ohe = ohe;
    }
}
