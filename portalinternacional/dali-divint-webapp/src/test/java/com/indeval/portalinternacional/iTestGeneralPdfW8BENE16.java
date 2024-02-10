package com.indeval.portalinternacional;

import java.io.FileOutputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class iTestGeneralPdfW8BENE16 extends TestCase {

    public void test() throws Exception {
        InputStream inputStream = iTestGeneralPdfW8BENE16.class.
                getResourceAsStream("/util/fw8bene2016.pdf");
        if( inputStream == null ) {
            throw new Exception("No se pudo encontrar el archivo");
        }
        PdfReader reader = new PdfReader(inputStream);
        if(reader!=null){
            FileOutputStream fos = new FileOutputStream("/home/lmunoz/registered6.pdf");
            PdfStamper stamp1 = new PdfStamper(reader, fos);
            AcroFields campos = stamp1.getAcroFields();

            //PAGE1
            campos.setField("topmostSubform[0].Page1[0].f1_01[0]","partIcmp1");
            campos.setField("topmostSubform[0].Page1[0].f1_02[0]","partIcmp2");
            campos.setField("topmostSubform[0].Page1[0].f1_03[0]","partIcmp3");


            campos.setField("topmostSubform[0].Page1[0].c1_01[0]","1");//partIcmp4a Corporation
            campos.setField("topmostSubform[0].Page1[0].c1_01[1]","2");//partIcmp4b Disregarded entity
            campos.setField("topmostSubform[0].Page1[0].c1_01[2]","3");//partIcmp4c Partnership
            campos.setField("topmostSubform[0].Page1[0].c1_01[3]","4");//partIcmp4d Simple trust
            campos.setField("topmostSubform[0].Page1[0].c1_01[4]","5");//partIcmp4e Grantor trust
            campos.setField("topmostSubform[0].Page1[0].c1_01[5]","6");//partIcmp4f Complex trust
            campos.setField("topmostSubform[0].Page1[0].c1_01[6]","7");//partIcmp4g Estate
            campos.setField("topmostSubform[0].Page1[0].c1_01[7]","8");//partIcmp4h Government
            campos.setField("topmostSubform[0].Page1[0].c1_01[8]","9");//partIcmp4i Central Bank of Issue
            campos.setField("topmostSubform[0].Page1[0].c1_01[9]","10");//partIcmp4j  Tax-exempt organization
            campos.setField("topmostSubform[0].Page1[0].c1_01[10]","11");//partIcmp4k Private foundation
            campos.setField("topmostSubform[0].Page1[0].c1_01[11]","12");//partIcmp4n International organization

            campos.setField("topmostSubform[0].Page1[0].c1_02[0]","1");//partIcmp4l yes
            campos.setField("topmostSubform[0].Page1[0].c1_02[1]","2");//partIcmp4m no


            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[0]","1");//partIcmp5a
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[1]","2");//partIcmp5b
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[2]","3");//partIcmp5c
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[3]","4");//partIcmp5d
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[4]","5");//partIcmp5e
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[5]","6");//partIcmp5f
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[6]","7");//partIcmp5g
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[7]","8");//partIcmp5h
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[8]","9");//partIcmp5i
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[9]","10");//partIcmp5j
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[10]","11");//partIcmp5k
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[11]","12");//partIcmp5l
            campos.setField("topmostSubform[0].Page1[0].Col1[0].c1_03[12]","13");//partIcmp5m

            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[0]","14");//partIcmp5a
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[1]","15");//partIcmp5b
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[2]","16");//partIcmp5c
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[3]","17");//partIcmp5d
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[4]","18");//partIcmp5e
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[5]","19");//partIcmp5f
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[6]","20");//partIcmp5g
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[7]","21");//partIcmp5h
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[8]","22");//partIcmp5i
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[9]","23");//partIcmp5j
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[10]","24");//partIcmp5k
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[11]","25");//partIcmp5l
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[12]","26");//partIcmp5m
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[13]","27");//partIcmp5n
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[14]","28");//partIcmp5o
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[15]","29");//partIcmp5p
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[16]","30");//partIcmp5q
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[17]","31");//partIcmp5r
            campos.setField("topmostSubform[0].Page1[0].Col2[0].c1_03[18]","32");//partIcmp5s

            campos.setField("topmostSubform[0].Page1[0].f1_04[0]","partIcmp6a");//partIcmp6a
            campos.setField("topmostSubform[0].Page1[0].f1_05[0]","partIcmp6b");//partIcmp6b
            campos.setField("topmostSubform[0].Page1[0].f1_06[0]","partIcmp6c");//partIcmp6c
            campos.setField("topmostSubform[0].Page1[0].f1_07[0]","partIcmp7a");//partIcmp7a
            campos.setField("topmostSubform[0].Page1[0].f1_08[0]","partIcmp7b");//partIcmp7b
            campos.setField("topmostSubform[0].Page1[0].f1_09[0]","partIcmp7c");//partIcmp7c
            campos.setField("topmostSubform[0].Page1[0].f1_10[0]","partIcmp8");//partIcmp8
            campos.setField("topmostSubform[0].Page1[0].f1_11[0]","partIcmp9a");//partIcmp9a
            campos.setField("topmostSubform[0].Page1[0].f1_12[0]","partIcmp9b");//partIcmp9b
            campos.setField("topmostSubform[0].Page1[0].f1_13[0]","partIcmp10");//partIcmp10

            //pagina 2
            campos.setField("topmostSubform[0].Page2[0].c2_01[0]","1");//partIIcmp11a
            campos.setField("topmostSubform[0].Page2[0].c2_01[1]","2");//partIIcmp11b
            campos.setField("topmostSubform[0].Page2[0].c2_01[2]","3");//partIIcmp11c
            campos.setField("topmostSubform[0].Page2[0].c2_01[3]","4");//partIIcmp11d
            campos.setField("topmostSubform[0].Page2[0].c2_01[4]","5");//partIIcmp11e

            campos.setField("topmostSubform[0].Page2[0].f2_01[0]","partIIcmp12a");//partIIcmp12a
            campos.setField("topmostSubform[0].Page2[0].f2_02[0]","partIIcmp12b");//partIIcmp12b
            campos.setField("topmostSubform[0].Page2[0].f2_03[0]","partIIcmp12c");//partIIcmp12c
            campos.setField("topmostSubform[0].Page2[0].f2_04[0]","partIIcmp13");//partIIcmp13

            campos.setField("topmostSubform[0].Page2[0].c2_06[0]","Yes");//partIIIcmp14a
            campos.setField("topmostSubform[0].Page2[0].f2_05[0]","partIIIcmp14b");//partIIIcmp14a1
            campos.setField("topmostSubform[0].Page2[0].c2_07[0]","Yes");//partIIIcmp14ba
            campos.setField("topmostSubform[0].Page2[0].c2_08[0]","1");//partIIIcmp14bb
            campos.setField("topmostSubform[0].Page2[0].c2_08[1]","2");//partIIIcmp14bc
            campos.setField("topmostSubform[0].Page2[0].c2_08[2]","3");//partIIIcmp14bd
            campos.setField("topmostSubform[0].Page2[0].c2_08[3]","4");//partIIIcmp14be
            campos.setField("topmostSubform[0].Page2[0].c2_08[4]","5");//partIIIcmp14bf
            campos.setField("topmostSubform[0].Page2[0].c2_08[5]","6");//partIIIcmp14bg
            campos.setField("topmostSubform[0].Page2[0].c2_08[6]","7");//partIIIcmp14bh
            campos.setField("topmostSubform[0].Page2[0].c2_08[7]","8");//partIIIcmp14bi
            campos.setField("topmostSubform[0].Page2[0].c2_08[8]","9");//partIIIcmp14bj
            campos.setField("topmostSubform[0].Page2[0].c2_08[9]","10");//partIIIcmp14bk
            campos.setField("topmostSubform[0].Page2[0].f2_06[0]","partIIIcmp14bl");//partIIIcmp14b1
            campos.setField("topmostSubform[0].Page2[0].c2_09[0]","Yes");//partIIIcmp14c
            campos.setField("topmostSubform[0].Page2[0].f2_07[0]","partIIIcmp15a");//partIIIcmp15a
            campos.setField("topmostSubform[0].Page2[0].f2_08[0]","partIIIcmp15b");//partIIIcmp15b
            campos.setField("topmostSubform[0].Page2[0].f2_09[0]","partIIIcmp15c");//partIIIcmp15c
            campos.setField("topmostSubform[0].Page2[0].f2_10[0]","partIIIcmp15d");//partIIIcmp15d
            campos.setField("topmostSubform[0].Page2[0].f2_11[0]","partIIIcmp15d");//partIIIcmp15d
            campos.setField("topmostSubform[0].Page2[0].f2_12[0]","partIIIcmp15d");//partIIIcmp15d
            campos.setField("topmostSubform[0].Page2[0].f2_13[0]","partIIIcmp16a");//partIVcmp16a
            campos.setField("topmostSubform[0].Page2[0].f2_14[0]","partIIIcmp16b");//partIVcmp16b
            campos.setField("topmostSubform[0].Page2[0].c2_10[0]","1");//partIVcmp17a
            campos.setField("topmostSubform[0].Page2[0].c2_10[1]","2");//partIVcmp17b

            //PAge 3
            campos.setField("topmostSubform[0].Page3[0].c3_01[0]","1");//partVcmp18
            campos.setField("topmostSubform[0].Page3[0].c3_02[0]","1");//partVIcmp19
            campos.setField("topmostSubform[0].Page3[0].f3_01[0]","partVIIcmp20");//partVIIcmp20
            campos.setField("topmostSubform[0].Page3[0].c3_03[0]","1");//partVIIcmp21
            campos.setField("topmostSubform[0].Page3[0].c3_04[0]","1");//partVIIIcmp22
            campos.setField("topmostSubform[0].Page3[0].c3_05[0]","1");//partXcmp23
            campos.setField("topmostSubform[0].Page3[0].c3_06[0]","1");//partXcmp24a

            //page 4
            campos.setField("topmostSubform[0].Page4[0].c4_01[0]","1");//partXcmp24b
            campos.setField("topmostSubform[0].Page4[0].c4_01[1]","2");//partXcmp24c
            campos.setField("topmostSubform[0].Page4[0].c4_02[0]","1");//partXcmp24d

            campos.setField("topmostSubform[0].Page4[0].c4_03[0]","Yes");//partXIcmp25a
            campos.setField("topmostSubform[0].Page4[0].c4_04[0]","1");//partXIcmp25b
            campos.setField("topmostSubform[0].Page4[0].c4_04[1]","2");//partXIcmp25c

            //page 5
            campos.setField("topmostSubform[0].Page5[0].c5_01[0]","1");//partXIIcmp26a
            campos.setField("topmostSubform[0].Page5[0].f5_01[0]","partXIIcmp26b");//partXIIcmp26b
            campos.setField("topmostSubform[0].Page5[0].c5_02[0]","1");//partXIIcmp26ba
            campos.setField("topmostSubform[0].Page5[0].c5_02[1]","2");//partXIIcmp26bb
            campos.setField("topmostSubform[0].Page5[0].f5_02[0]","partXIIcmp26c");//partXIIcmp26c
            campos.setField("topmostSubform[0].Page5[0].f5_03[0]","partXIIcmp26d");//partXIIcmp26d
            campos.setField("topmostSubform[0].Page5[0].f5_04[0]","partXIIcmp26e");//partXIIcmp26e
            campos.setField("topmostSubform[0].Page5[0].c5_03[0]","1");//partXIIIcmp27
            campos.setField("topmostSubform[0].Page5[0].c5_04[0]","1");//partXIVcmp28a
            campos.setField("topmostSubform[0].Page5[0].c5_04[1]","2");//partXIVcmp28b
            campos.setField("topmostSubform[0].Page5[0].c5_05[0]","1");//partXVcmp29a
            campos.setField("topmostSubform[0].Page5[0].c5_05[1]","2");//partXVcmp29b
            campos.setField("topmostSubform[0].Page5[0].c5_05[2]","3");//partXVcmp29c

            //page 6
            campos.setField("topmostSubform[0].Page6[0].c5_05[0]","4");//partXVcmp29d
            campos.setField("topmostSubform[0].Page6[0].c5_05[1]","5");//partXVcmp29e
            campos.setField("topmostSubform[0].Page6[0].c5_05[2]","6");//partXVcmp29f
            campos.setField("topmostSubform[0].Page6[0].c6_01[0]","1");//partXVIcmp30a
            campos.setField("topmostSubform[0].Page6[0].c6_02[0]","1");//partXVIIcmp31a
            campos.setField("topmostSubform[0].Page6[0].c6_03[0]","1");//partXVIIIcmp32a
            campos.setField("topmostSubform[0].Page6[0].c6_04[0]","1");//partXIXcmp33a
            campos.setField("topmostSubform[0].Page6[0].f6_01[0]","partXIXcmp33b");//partXXcmp33b
            campos.setField("topmostSubform[0].Page6[0].c6_05[0]","1");//partXXcmp34a
            campos.setField("topmostSubform[0].Page6[0].f6_02[0]","partXXcmp34b");//partXXcmp34b

            //page 7
            campos.setField("topmostSubform[0].Page7[0].c7_01[0]","1");//partXXIcmp35a
            campos.setField("topmostSubform[0].Page7[0].f7_01[0]","partXXIcmp35b");//partXXIcmp35b
            campos.setField("topmostSubform[0].Page7[0].c7_02[0]","1");//partXXIIcmp36a
            campos.setField("topmostSubform[0].Page7[0].c7_03[0]","1");//partXXIIIcmp37a
            campos.setField("topmostSubform[0].Page7[0].f7_02[0]","partXXIIIcmp37a1");//partXXIIIcmp37a1
            campos.setField("topmostSubform[0].Page7[0].c7_03[1]","2");//partXXIIIcmp37b
            campos.setField("topmostSubform[0].Page7[0].f7_03[0]","partXXIIIcmp37ba");//partXXIIIcmp37ba
            campos.setField("topmostSubform[0].Page7[0].f7_04[0]","partXXIIIcmp37bb");//partXXIIIcmp37bb
            campos.setField("topmostSubform[0].Page7[0].c7_04[0]","1");//partXXIVcmp38
            campos.setField("topmostSubform[0].Page7[0].c7_05[0]","Yes");//partXXVcmp39
            campos.setField("topmostSubform[0].Page7[0].c7_06[0]","1");//partXXVIcmp40a
            campos.setField("topmostSubform[0].Page7[0].c7_07[0]","1");//partXXVIcmp40b
            campos.setField("topmostSubform[0].Page7[0].c7_07[1]","2");//partXXVIcmp40c

            //page 8
            campos.setField("topmostSubform[0].Page8[0].c8_01[0]","1");//partXXVIIcmp41a
            campos.setField("topmostSubform[0].Page8[0].f8_01[0]","partXXVIIIcmp42a");//partXXVIIIcmp42a
            campos.setField("topmostSubform[0].Page8[0].f8_01[0]","partXXVIIIcmp42b");//partXXVIIIcmp42b
            campos.setField("topmostSubform[0].Page8[0].c8_02[0]","1");//partXXVIIIcmp43a
            //tabla
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow1[0].f8_03[0]","partXXIXnoma");//partXXIXnoma
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow1[0].f8_04[0]","partXXIXadra");//partXXIXadra
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow1[0].f8_05[0]","part29tina");//part29tina
            //2
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow2[0].f8_06[0]","partXXIXnomb");//partXXIXnomb
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow2[0].f8_07[0]","partXXIXadrb");//partXXIXadrb
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow2[0].f8_08[0]","part29tinb");//part29tinb
            //3
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow3[0].f8_09[0]","partXXIXnomc");//partXXIXnomc
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow3[0].f8_10[0]","partXXIXadrc");//partXXIXadrc
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow3[0].f8_11[0]","part29tinc");//part29tinc
            //4
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow4[0].f8_12[0]","partXXIXnomd");//partXXIXnomd
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow4[0].f8_13[0]","partXXIXadrd");//partXXIXadrd
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow4[0].f8_14[0]","part29tind");//part29tind
            //5
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow5[0].f8_15[0]","partXXIXnome");//partXXIXnome
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow5[0].f8_16[0]","partXXIXadre");//partXXIXadre
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow5[0].f8_17[0]","part29tine");//part29tine
            //6
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow6[0].f8_18[0]","partXXIXnomf");//partXXIXnomf
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow6[0].f8_19[0]","partXXIXadrf");//partXXIXadrf
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow6[0].f8_20[0]","part29tinf");//part29tinf
            //7
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow7[0].f8_21[0]","partXXIXnomg");//partXXIXnomg
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow7[0].f8_22[0]","partXXIXadrg");//partXXIXadrg
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow7[0].f8_23[0]","part29ting");//part29ting
            //8
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow8[0].f8_24[0]","partXXIXnomh");//partXXIXnomh
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow8[0].f8_25[0]","partXXIXadrh");//partXXIXadrh
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow8[0].f8_26[0]","part29tinh");//part29tinh
            //9
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow9[0].f8_27[0]","partXXIXnomi");//partXXIXnomi
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow9[0].f8_28[0]","partXXIXadri");//partXXIXadri
            campos.setField("topmostSubform[0].Page8[0].Table_Part29[0].BodyRow9[0].f8_29[0]","part29tini");//part29tini

            //campos.setField("topmostSubform[0].Page8[0].Table_Part29[0]","partXXXsign");//partXXXsign
            campos.setField("topmostSubform[0].Page8[0].c8_03[0]","1");//certify

            stamp1.close();
            fos.close();
            reader.close();

        }
    }
}
