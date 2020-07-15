package converter;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
       
       String initBase = "";
       String num = "";
       String expectBase = "";
       
       
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()) {
                initBase = scanner.nextLine();
                if (testBase(initBase)) {
                    errorExit();
                }
            } else {
                errorExit();
                }

            if(scanner.hasNextLine()) {
                num = scanner.nextLine();
                if (testExists(num)) {
                    errorExit();
                }
            } else {
                errorExit();
                }

            if(scanner.hasNextLine()) {
            expectBase = scanner.nextLine();
            if (testBase(expectBase)) {
                errorExit();
            }
        } else {
                errorExit();
            }
    
    
    
        if (num.contains(".")) {
            forDecimals(initBase, num, expectBase);
        } else {
            forInt(initBase, num, expectBase);
        }
    }
    public static boolean testBase(String base){
        if(base.matches("[0-9]+")){
            int n = Integer.parseInt(base);
            return n <= 0 || n > Character.MAX_RADIX;
        }
        return true;
    }

    public static boolean testExists(String str){
        return str == null || str.length() == 0;
    }

    public static void errorExit() {
        System.out.println("error");
        System.exit(0);
    }
    
    public static void forInt(String initBase, String num, String expectBase) {
        int res = 0;
        if (initBase.equals("1")) {
            System.out.println(Integer.toString(num.length(), Integer.parseInt(expectBase)));
        } else if (expectBase.equals("1")) {
            for (int i = 0; i < Integer.parseInt(num); i++) {
                System.out.print("1");
            }
        } else {
            if (!initBase.equals("10")) {
                res = Integer.parseInt(num, Integer.parseInt(initBase));
                System.out.println(Integer.toString(res, Integer.parseInt(expectBase)));
            } else {
                System.out.println(Integer.toString(Integer.parseInt(num), Integer.parseInt(expectBase)));
            }
        }
    }

    public static void forDecimals(String initBase, String num, String expectBase) {

        //integer part of the decimal
        String firstPart = "";
        boolean ifhasFirstPart = false;
        if (!(num.substring(0, num.indexOf(".")).equals("0"))) {
            String firstInt = num.substring(0, num.indexOf("."));
            int cbbase = Integer.parseInt(firstInt, Integer.parseInt(initBase));
            firstPart = Integer.toString(cbbase, Integer.parseInt(expectBase));
            ifhasFirstPart = true;
        }

        //if initbase is not in base 10
        if(!initBase.equals("10")){
            num = num.substring(num.indexOf(".") + 1);
            double bs = Double.parseDouble(initBase);
            int bb = Integer.parseInt(initBase);
            double total = 0;
            for (int i = 0; i < num.length(); i++) {
                double res = Character.getNumericValue(num.charAt(i)) / bs;
                total += res;
                bs *= bb;
            }
            String newNum = String.valueOf(total);
            num = newNum;
        }

        //Decimal part
        num = num.substring(num.indexOf("."));
        double num1 = Double.parseDouble(num);
        String res1 = ".";
        BigDecimal b1 = new BigDecimal(String.valueOf(num1));
        for (int i = 0; i < 5; i++) {
            b1 = b1.multiply(new BigDecimal(expectBase));
            int intValue = b1.intValue();
            res1 += String.valueOf(Character.forDigit(intValue, 36));
            b1 = b1.subtract(new BigDecimal(intValue));
        }

        if(expectBase.equals("1")){
            for(int i =0 ; i < Integer.parseInt(firstPart); i++){
                System.out.print("1");
            }
        }else{
            if (ifhasFirstPart) {
                System.out.println(firstPart +" " +res1);
            } else {
                System.out.println("0"+ res1);
            }
        }

    }

}
