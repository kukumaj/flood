package com.epam.rd.autocode.floodfill;

import java.util.ArrayList;
import java.util.List;


public class FloodFillImpl implements FloodFill {
    @Override
    public void flood(String map, FloodLogger logger) {
        logger.log(map);

        List<List<Field>> listOfFields = new ArrayList<>();
        {
            // convert fields map to 2d list
            List<Field> lastSubList = new ArrayList<>();
            listOfFields.add(lastSubList);
            for (int i = 0; i < map.length(); i++) {
                char ch = map.charAt(i);
                switch (ch) {
                    case WATER:
                        lastSubList.add(Field.WATER);
                        break;
                    case LAND:
                        lastSubList.add(Field.LAND);
                        break;
                    case '\n':
                        lastSubList = new ArrayList<>();
                        listOfFields.add(lastSubList);
                        break;
                }
            }
        }

        List<List<Field>> updatedListOfFields = new ArrayList<>();
        {
            // create deep copy
            for (List<Field> row : listOfFields) {
                updatedListOfFields.add(new ArrayList<>(row));
            }
        }

        // update list of fields
        for (int i = 0; i < listOfFields.size(); i++) {
            for (int j = 0; j < listOfFields.get(0).size(); j++) {
                Field field = listOfFields.get(i).get(j);
                if (field == Field.WATER) {
                    if (!(i - 1 < 0)) {
                        updatedListOfFields.get(i - 1).set(j, Field.WATER);
                    }
                    if (!(i + 1 > listOfFields.size() - 1)) {
                        updatedListOfFields.get(i + 1).set(j, Field.WATER);
                    }
                    if (!(j - 1 < 0)) {
                        updatedListOfFields.get(i).set(j - 1, Field.WATER);
                    }
                    if (!(j + 1 > listOfFields.get(0).size() - 1)) {
                        updatedListOfFields.get(i).set(j + 1, Field.WATER);
                    }
                }
            }
        }

        for (int i = 0; i < updatedListOfFields.size(); i++) {
            for (int j = 0; j < updatedListOfFields.get(0).size(); j++) {
                Field field = updatedListOfFields.get(i).get(j);
                if (field == Field.LAND) {
                    flood(fields2dListToString(updatedListOfFields), logger);
                    return;
                }
            }
        }

        logger.log(fields2dListToString(updatedListOfFields));
    }

    private static String fields2dListToString(List<List<Field>> fields) {
        StringBuilder result = new StringBuilder();
        for (List<Field> row : fields) {
            result.append("\n");
            for (Field column : row) {
                switch (column) {
                    case LAND:
                        result.append(LAND);
                        break;
                    case WATER:
                        result.append(WATER);
                        break;
                }
            }
        }

        return result.substring(1);
    }

    public static void main(String[] args) {
        FloodFill a = new FloodFillImpl();
        a.flood("█████\n█░░░█\n█████", floodState -> System.out.println(floodState + "\n"));
    }
}
