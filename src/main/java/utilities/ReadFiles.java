package utilities;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Create By: chaule
 * Create At: 9Nov2023
 * Notes:
 */
public class ReadFiles {
    private static List<String> pages;
    private static List<String> labels;
    private static List<String> ens;
    private static List<String> vns;
    private static List<String> ths;

    public static void setLocalizationData() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "/resources/.csv"));
        List<String[]> listLocalization = reader.readAll();
        System.out.println("Total rows which we have is " + listLocalization.size());
        // create Iterator reference
        Iterator<String[]> iterator = listLocalization.iterator();
        pages = new ArrayList<String>();
        labels = new ArrayList<String>();
        ens = new ArrayList<String>();
        vns = new ArrayList<String>();
        // Iterate all values
        while (iterator.hasNext()) {
            String[] str = iterator.next();
//            System.out.print(" Values are ");
            for (int i = 0; i < str.length; i++) {
//                System.out.print(" "+str[i]);
                switch (i) {
                    case 0:
                        pages.add(str[i]);
                        break;
                    case 1:
                        labels.add(str[i]);
                        break;
                    case 2:
                        ens.add(str[i]);
                        break;
                    case 3:
                        vns.add(str[i]);
                        break;
                    default:
                        break;
                }
            }
//            System.out.println("   ");
        }
    }

    public static void setLocalizationPartnerTabletData() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "/resources/.csv"));
        List<String[]> listLocalization = reader.readAll();
        System.out.println("Total rows which we have is " + listLocalization.size());
        // create Iterator reference
        Iterator<String[]> iterator = listLocalization.iterator();
        pages = new ArrayList<String>();
        labels = new ArrayList<String>();
        ens = new ArrayList<String>();
        vns = new ArrayList<String>();
        // Iterate all values
        while (iterator.hasNext()) {
            String[] str = iterator.next();
//            System.out.print(" Values are ");
            for (int i = 0; i < str.length; i++) {
//                System.out.print(" "+str[i]);
                switch (i) {
                    case 0:
                        pages.add(str[i]);
                        break;
                    case 1:
                        labels.add(str[i]);
                        break;
                    case 2:
                        ens.add(str[i]);
                        break;
                    case 3:
                        vns.add(str[i]);
                        break;
                    default:
                        break;
                }
            }
//            System.out.println("   ");
        }
    }

    public static void setLocalizationPartnerPhoneData() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "/resources/.csv"));
        List<String[]> listLocalization = reader.readAll();
        System.out.println("Total rows which we have is " + listLocalization.size());

        Iterator<String[]> iterator = listLocalization.iterator();
        pages = new ArrayList<String>();
        labels = new ArrayList<String>();
        ens = new ArrayList<String>();
        vns = new ArrayList<String>();
        ths = new ArrayList<String>();

        while (iterator.hasNext()) {
            String[] str = iterator.next();

            for (int i = 0; i < str.length; i++) {
                switch (i) {
                    case 0:
                        pages.add(str[i]);
                        break;
                    case 1:
                        labels.add(str[i]);
                        break;
                    case 2:
                        ens.add(str[i]);
                        break;
                    case 3:
                        vns.add(str[i]);
                        break;
                    case 4:
                        ths.add(str[i]);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public List<String> getLocalizePage() {
        return this.pages;
    }

    public List<String> getLocalizeLabel() {
        return this.labels;
    }

    public List<String> getLocalizeEN() {
        return this.ens;
    }

    public List<String> getLocalizeVN() {
        return this.vns;
    }

    public List<String> getLocalizeTH() {
        return this.ths;
    }
}
