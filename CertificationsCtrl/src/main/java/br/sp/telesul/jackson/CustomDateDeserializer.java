/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.sp.telesul.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import br.sp.telesul.config.SystemConfig;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lmohan
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private SimpleDateFormat formatter = new SimpleDateFormat(SystemConfig.DATE_FORMAT);
    private SimpleDateFormat formatterNoDay = new SimpleDateFormat(SystemConfig.DATE_FORMAT_NO_DAY);
    private int firstIndex = -1;
    private int secondIndex = -1;

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String date = jsonparser.getText();

        try {
            firstIndex = date.indexOf("-");
            if (firstIndex > -1) {
                secondIndex = date.indexOf("-", firstIndex + 1);
                if (secondIndex > -1) {
                    return formatter.parse(date);
                } else {
                    return formatterNoDay.parse(date);
                }
            }
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
