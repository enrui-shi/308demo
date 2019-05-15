package com.example.demo.temp;

import com.example.demo.Entity.Cluster;
import com.example.demo.Entity.ClusterEdge;

import java.util.List;

public class GVAL {
    public static List<Cluster> nj;
    public static List<ClusterEdge> nje;
    public static List<Cluster> ny;
    public static List<ClusterEdge> nye;
    public static List<Cluster> oh;
    public static List<ClusterEdge> ohe;

    public static List<Cluster> getNj() {
        return nj;
    }

    public static void setNj(List<Cluster> nj) {
        GVAL.nj = nj;
    }

    public static List<ClusterEdge> getNje() {
        return nje;
    }

    public static void setNje(List<ClusterEdge> nje) {
        GVAL.nje = nje;
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
