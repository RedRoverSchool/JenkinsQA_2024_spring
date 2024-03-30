package school.redrover;

public class CountTotalNumber {
        public static void main (String[] args) {
            String str = "I enjoy coding and java is everything"; //7 words

            //convert string to String arra using split method and pass (space) as parameter
            String[] strArray = str.split(" ");

            //the length of the string array will be the  total word COUNT
            System.out.println("Word Count: " + strArray.length);

            System.out.println("*******************");
            //incasw if we need to print each word then we have to loop through the strArray
            for ( int i = 0; i < strArray.length; i++){
                System.out.println(strArray[i]);
            }
            System.out.println("*******************");
        }
        public int getCountTotal (String str){
            int result = 0;
            // convert strung to String arr using split method & pass as parametr
            String[] strArray = str.split(" ");
            result = strArray.length;
            return result;
        }
    }

