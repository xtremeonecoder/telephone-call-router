/**
 * Telephone Call Router - Java Application
 *
 * @category   Java_Application
 * @package    telephone-call-router
 * @author     Suman Barua
 * @developer  Suman Barua <sumanbarua576@gmail.com>
 **/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package programmingexercise;
import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Scanner;


/**
 * Finding cheapest call rate for a particular number
 * @author Suman
 */
public class ProgrammingExercise {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Get telephone number as user input
        Scanner inputObject = new Scanner(System.in); // Create a Scanner object
        System.out.print("Enter Telephone Number (e.g: 68123456789): ");
        String phoneNumber = inputObject.nextLine();  // Read user input
        phoneNumber = phoneNumber.replaceAll("\\D", ""); // Remove all non-digit characters (if there is any)
        
        // Prepare price-list according to operators
        // Here I used Hashtable for the ease of data storing and manipulation
        Hashtable operators = getTelephoneOperators();
                
        // Iterate all the existing price-lists according to operators
        // Explore all the price items, and calculate the cheapest one
        Enumeration operatorNames = operators.keys(); // Operators' names
        Enumeration operatorItems = operators.elements(); // Operators as item list
        Map<String, Double> matchedItems = new HashMap<String, Double>(); // All cheap operators
        
        // Iterate through the available operators and explore their price-list
        while( operatorItems.hasMoreElements() ) { 
            // Get operator's price-list
            Hashtable operator = (Hashtable) operatorItems.nextElement();  
            Enumeration prefixList = operator.keys(); // Prefix list of a particular operator
            Enumeration priceList = operator.elements(); // Price list of a particular operator
  
            // Necessary variables initialization
            int maxPrefixLength = 0;
            Double leastPrice = 0.0;
            String leastPrefix = "null";
            
            // Iterate through price-list of an operator
            while( prefixList.hasMoreElements() ) { 
                // Evaluate the least price per operator
                Double price = (Double) priceList.nextElement();
                String prefix = (String) prefixList.nextElement();
                
                // Check if the prefix matches the number or not.
                // Check if the previously matched prefix length
                // is smaller than the currently matched prefix length
                // We will consider the maximum-length matched prefix
                if( phoneNumber.matches(prefix+"(.*)") 
                        && (maxPrefixLength < prefix.length()) ) {
                    leastPrice = price;
                    leastPrefix = prefix;
                    maxPrefixLength = prefix.length();
                }                                
            }  
            
            // Prepare least-price object as per operator
            if( !leastPrefix.equals("null") )
                matchedItems.put((String) operatorNames.nextElement(), leastPrice);
            else
                operatorNames.nextElement();
        }
        
        // List down the found least-price operators
        if( matchedItems.size() > 0 ) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Total "+matchedItems.size()+" operator(s) found for the number \""+phoneNumber+"\"");
            for(Map.Entry<String, Double> entry: matchedItems.entrySet())
                System.out.println("=> "+entry.getKey() + " offers $" + entry.getValue()+"/minute");
        }
        
        // Calculate the cheapest operator among the cheaps
        System.out.println("------------------------------------------------------------------------");
        
        // Get cheapest call rate
        String result[] = calculateCheapestCallRate(matchedItems);
        if( !result[0].equals("") )
            System.out.println("Cheapest operator is \""+result[0]+"\", which offers $"+result[1]+"/minute\n");
        else
            System.out.println("Sorry, no operator found!\n");        
    }

    /**
     * Prepare price-list according to operators
     * @return available operators and their price lists
     */
    public static Hashtable getTelephoneOperators() {
        // Here I used Hashtable for the ease of data storing and manipulation
        // Initialize Operator-A
        Hashtable<String, Double> OperatorA = new Hashtable<String, Double>();
        OperatorA.put("1", 0.9);
        OperatorA.put("268", 5.1);
        OperatorA.put("46", 0.17);
        OperatorA.put("4620", 0.0);
        OperatorA.put("468", 0.15);
        OperatorA.put("4631", 0.15);
        OperatorA.put("4673", 0.9);
        OperatorA.put("46732", 1.1);
        
        // Initialize Operator-B
        Hashtable<String, Double> OperatorB = new Hashtable<String, Double>();
        OperatorB.put("1", 0.92);
        OperatorB.put("44", 0.5);
        OperatorB.put("46", 0.2);
        OperatorB.put("467", 1.0);
        OperatorB.put("48", 1.2);

        // Uncomment Operator-C and Operator-D if you want to check with more operators
        // // Initialize Operator-C
        // Hashtable<String, Double> OperatorC = new Hashtable<String, Double>();
        // OperatorC.put("1", 2.0);
        // OperatorC.put("44", 1.0);
        // OperatorC.put("46", 3.0);
        // OperatorC.put("46725", 4.0);
        // OperatorC.put("467", 1.2);
        // OperatorC.put("4672", 2.0);

        // // Initialize Operator-D
        // Hashtable<String, Double> OperatorD = new Hashtable<String, Double>();
        // OperatorD.put("46", 4.0);
        // OperatorD.put("467", 2.0);
        // OperatorD.put("46725", 3.0);
        // OperatorD.put("4672", 1.0);
        // OperatorD.put("48", 3.0);
        
        // Serialized the price-lists in the Hashtable as per operator's name
        Hashtable<String, Hashtable> operators = new Hashtable<String, Hashtable>();
        operators.put("Operator-A", OperatorA); // Adding Operator-A to HashTable
        operators.put("Operator-B", OperatorB); // Adding Operator-B to HashTable
        
        // Uncomment Operator-C and Operator-D if you want to check with more operators
        // operators.put("Operator-C", OperatorC); // Adding Operator-C to HashTable
        // operators.put("Operator-D", OperatorD); // Adding Operator-D to HashTable
        
        // return available operators to the main function
        return operators;
    }
    
    /**
     * Calculate the cheapest operator
     * @param matchedItems is the HashMap object
     * @return String type array
     */
    public static String[] calculateCheapestCallRate(Map<String, Double> matchedItems){ 
        // Necessary variables initialization
        String cheapestOperator = "";
        double cheapestPrice = 999999.0;
        String result[] = new String[2];
        
        // Iterate throgh the mapped items and find the cheapest price
        for(Map.Entry<String, Double> entry: matchedItems.entrySet()){
            if( entry.getValue() < cheapestPrice ) { 
              cheapestPrice = entry.getValue();
              cheapestOperator = entry.getKey();
            } 
        } 
        
        // Return result
        result[0] = cheapestOperator;
        result[1] = new Double(cheapestPrice).toString();
        return result; 
    }     
}
