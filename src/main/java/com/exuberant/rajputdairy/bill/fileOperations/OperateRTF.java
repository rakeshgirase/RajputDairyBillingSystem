/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.exuberant.rajputdairy.bill.fileOperations;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.style.RtfFont;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 13411
 */
public class OperateRTF {
    private String fontName = "Shivaji01";
    private float fontSize = 20;
    private RtfFont font = new RtfFont(fontName,fontSize );
public void appendWordFile(Document doc,String content){        
        try {
            doc.add(new Paragraph(content, font));
        } catch (DocumentException ex) {
            Logger.getLogger(OperateRTF.class.getName()).log(Level.SEVERE, null, ex);
        }    
}
}
