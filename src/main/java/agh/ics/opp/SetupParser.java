package agh.ics.opp;

import agh.ics.opp.simulation.types.SimulationSetup;

import java.util.ArrayList;
import java.util.List;

public class SetupParser {

    private boolean strToBoolean(String s) throws IllegalArgumentException{
        if(s.equals("false") || s.equals("true"))
            return Boolean.parseBoolean(s);
        throw new IllegalArgumentException(s+" - is not a boolean value");
    }

    private int strToInt(String s) throws IllegalArgumentException{
        try{
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(s + " - is not a int value");
        }
    }

    public SimulationSetup parseSetup(String[] lines) {
        List<String> args = new ArrayList<>();
        try {
            if(lines.length != 16) {
                throw new IllegalArgumentException("number of lines is not equal to 16");
            }
            int i=1;
            for (String line: lines){
                line = line.trim();
                if(line.isEmpty() || line.equals("="))
                    throw new IllegalArgumentException("line "+i+" is empty or has only '='");
                String[] strings = line.split("=");
                if (strings.length == 1 && line.charAt(0) == '='){
                    args.add(strings[0]);
                }
                else if (strings.length == 2){
                    args.add(strings[1]);
                }
                else {
                    throw new IllegalArgumentException(i+" line has an incorrect construction");
                }
                i++;
            }
        } catch (IllegalArgumentException e){
            System.out.println("Setup parse error: "+e.getMessage());
            return null;
        }
        int i = 0;
        try {
            return new SimulationSetup(
                    strToBoolean(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToBoolean(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToBoolean(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToBoolean(args.get(i++)),
                    strToInt(args.get(i++)),
                    strToInt(args.get(i++))
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Setup parse error at line " + i + ": " + e.getMessage());
            return null;
        }
    }
}
