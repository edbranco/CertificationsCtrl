/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import java.util.List;

/**
 *
 * @author ebranco
 */
public class ObjectReturn {

    private Long totalEntities;
    private List<?> objects;

    public ObjectReturn() {
    
    }
    public ObjectReturn(Long totalEntities, List<?> objects) {
        this.totalEntities = totalEntities;
        this.objects = objects;
    }

    public Long getTotalEntities() {
        return totalEntities;
    }

    public void setTotalEntities(Long totalEntities) {
        this.totalEntities = totalEntities;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }
    
    
}
