/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.model;

import java.util.List;
import org.springframework.data.domain.Page;


/**
 *
 * @author ebranco
 */
public class FilterReturn {

    private Long totalEntities;
    private Page page;

    public Long getTotalEntities() {
        return totalEntities;
    }

    public void setTotalEntities(Long totalEntities) {
        this.totalEntities = totalEntities;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }



}
