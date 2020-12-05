import java.util.Scanner;

public class Gradebook {
    public static void main(String []args) {
     
        //variable declaration and initialization
        String classTemp;
        String creditsHolder;
        String creditsTemp;
        boolean classSizeReq = false;
        boolean classesReq = false;
        boolean userVibing = true;
        boolean hasClass = false;
        boolean percentDomain;
        int classSize = 0;
        int classCounter = 0;
        int userLargeReq = 0;
        int weight = 0;
        int switchIndex = 0;
        double classGradesTemp;
        double gradeTemp;
        double[] cg;
        String[] classSwitch;
        double[] gradeSwitch;
        int[] creditsSwitch;

        //user inputs classes, credits, and percent grades
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Gradebook!");
        while (!classSizeReq) {
            System.out.println("How many classes are you taking this year?");
            classSize = Integer.parseInt(scan.nextLine());
            if (classSize >= 1) {
                classSizeReq = true;
            } else 
                System.out.println("Please choose a number greater than or equal to 1");
        }

        String[] classes = new String[classSize];
        double[] classGrades = new double[classSize];
        int[] credits = new int[classSize];
        for (int i = 0; i < classSize; i++) {
            System.out.println("Please input class #" + (i+1) + " that you are taking this year.");
            classes[i] = scan.nextLine();
            if ((classes[i].toUpperCase().indexOf("AP ") != -1) || (classes[i].toUpperCase().indexOf("AT ") != -1)) {
                weight++;
            }
            System.out.println("How many credits is this class? Click return for 1 or type in a different number.");
            creditsHolder = scan.nextLine();
            if (creditsHolder.equals("")) credits[i] = 1;
            else credits[i] = Integer.parseInt(creditsHolder);
            while (credits[i] <= 0) {
                System.out.println("Enter a credit value greater than 0.");
                credits[i] = Integer.parseInt(scan.nextLine());
            }
            System.out.println("Enter your current PERCENT grade (out of 90) for " + classes[i] + ": ");
            classGrades[i] = Double.parseDouble(scan.nextLine());
            while (classGrades[i] < 0 || classGrades[i] > 90) {
                System.out.println("Enter a percentage from 0 - 90.");
                classGrades[i] = Double.parseDouble(scan.nextLine());
            }
                
        }

        //checkpoint #1, use only when testing code
        /*
        for (int i = 0; i < classes.length; i++) {
            System.out.print(classes[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < classGrades.length; i++) {
            System.out.print(classGrades[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < credits.length; i++) {
            System.out.print(credits[i] + " ");
        }
        System.out.println();
        */

        //loop until user is done manipulating their grades (hence the user is done vibing)
        while (userVibing) {
            System.out.println("Which of the following actions would you like to take?");
            System.out.print("[1] Check Weighted GPA, [2] Check Unweighted GPA, [3] Add/Remove a Class, [4] Change Grade,");
            System.out.println(" [5] Add Hypothetical Assignment, [6] Study Plan, [7] Exit");
            userLargeReq = Integer.parseInt(scan.nextLine());

            //runs methods based on user input
            if (userLargeReq == 1) {
                System.out.println("Your GPA is " + Math.floor(checkGPA(classGrades, credits, true, weight) * 100) / 100);
            } else if (userLargeReq == 2) {
                System.out.println("Your GPA is " + Math.floor(checkGPA(classGrades, credits, false, weight) * 100) / 100);
            } else if (userLargeReq == 3) {
                //adding/removing can't be done in a method, requires returning arrays of multiple types
                //method without using ArrayList
                System.out.println("What class would you like to add/remove?");
                classTemp = scan.nextLine();
                hasClass = false;
                for (int i = 0; i < classes.length; i++) {
                    if (classTemp.toUpperCase().equals(classes[i].toUpperCase())) {
                        hasClass = true;
                        switchIndex = i;
                    }
                }
                if (hasClass) {
                    System.out.println("Removing class: " + classTemp + "...");
                    classSwitch = new String[classes.length-1];
                    gradeSwitch = new double[classes.length-1];
                    creditsSwitch = new int[classes.length-1];
                    if ((classTemp.toUpperCase().indexOf("AP ") != -1) || (classTemp.toUpperCase().indexOf("AT ") != -1)) {
                        weight--;
                    }
                    for (int j = 0; j < classes.length; j++) {
                        if (j < switchIndex) {
                            classSwitch[j] = classes[j];
                            gradeSwitch[j] = classGrades[j];
                            creditsSwitch[j] = credits[j];
                        } else if (j > switchIndex) {
                            classSwitch[j-1] = classes[j];
                            gradeSwitch[j-1] = classGrades[j];
                            creditsSwitch[j-1] = credits[j];
                        }
                    }
                    classes = classSwitch;
                    classGrades = gradeSwitch;
                    credits = creditsSwitch;
                    System.out.println("Done");
                } else {
                    classSwitch = new String[classes.length+1];
                    gradeSwitch = new double[classes.length+1];
                    creditsSwitch = new int[classes.length+1];
                    if ((classTemp.toUpperCase().indexOf("AP ") != -1) || (classTemp.toUpperCase().indexOf("AT ") != -1)) {
                        weight++;
                    }

                    System.out.println("How many credits is this class? Click return for 1 or type in a different number.");
                    creditsTemp = scan.nextLine();

                    System.out.println("Enter your current PERCENT grade (out of 90) for " + classTemp + ": ");
                    gradeTemp = Double.parseDouble(scan.nextLine());

                    for (int j = 0; j < classes.length; j++) {
                        classSwitch[j] = classes[j];
                        gradeSwitch[j] = classGrades[j];
                        creditsSwitch[j] = credits[j];
                    }
        
                    classSwitch[classes.length] = classTemp;
                    gradeSwitch[classes.length] = gradeTemp;
                    if (creditsTemp.equals("")) creditsSwitch[classes.length] = 1;
                    else creditsSwitch[classes.length] = Integer.parseInt(creditsTemp);

                    classes = classSwitch;
                    classGrades = gradeSwitch;
                    credits = creditsSwitch;
        
                    System.out.println("Done!");
                }
            } else if (userLargeReq == 4) {
                cg = changeGrade(classes, classGrades, scan);
                classGrades[(int)(cg[0])] = cg[1];
            } else if (userLargeReq == 5) {
                classGrades = imagAssign(classes, classGrades, credits, weight, scan);
            } else if (userLargeReq == 6) {
                optimizeTime(classes, classGrades, credits, scan);
            } else if (userLargeReq == 7) {
                //option to break while loop
                System.out.println("Bye bye! Your grades will not be stored.");
                userVibing = false;
            }
        }

    }

    //method to check either the weighted or unweighted GPA
    //INPUTS: grades, credits, weighted/unweighted, # of weighted classes
    //OUTPUTS: double GPA 
    public static double checkGPA(double[] grades, int[] credits, boolean weighted, int weight) {
        double sum = 0;
        double creditsSum = 0;

        for (int i = 0; i < grades.length; i++) {
            if (grades[i] >= 85) {
                sum += 4.5 * credits[i];
            } else if (grades[i] >= 75) {
                sum += 4 * credits[i];
            } else if (grades[i] >= 65) {
                sum += 3.5 * credits[i];
            } else if (grades[i] >= 55) {
                sum += 3 * credits[i];
            } else if (grades[i] >= 45) {
                sum += 2.5 * credits[i];
            } else if (grades[i] >= 35) {
                sum += 2 * credits[i];
            } else if (grades[i] >= 25) {
                sum += 1.5 * credits[i];
            } else if (grades[i] >= 15) {
                sum += 1 * credits[i];
            }

            creditsSum += credits[i];
        }

        if (weighted) sum += (weight * 0.5);

        return (sum / creditsSum);

    }

    //method to change a grade of one class if a new assignment comes in or user made a mistake while inputting classes
    //INPUT: classes, grades, scanner 
    //OUTPUT: double array, index 0 = index of class to be changed, index 1 = new grade for that class
    public static double[] changeGrade(String[] classes, double[] grades, Scanner scan) {
        
        double[] returnA = new double[2];

        int userReq = 0;
        System.out.println("Which of the following class grades would you like to change?");
        for (int i = 0; i < classes.length; i++) {
            System.out.println("[" + (i+1) + "] " + classes[i] + " - " + grades[i]);
        }
        userReq = Integer.parseInt(scan.nextLine());
        while (userReq < 1 || userReq > classes.length) {
            System.out.println("Select one of the classes please.");
            userReq = Integer.parseInt(scan.nextLine());
        }

        returnA[0] = userReq - 1;

        System.out.println("Enter the new grade:");
        returnA[1] = Double.parseDouble(scan.nextLine());
        System.out.println("Done!");

        return returnA;

    }

    //method to add a hypothetical assignment with some percentage weight to the current percentage in any class
    //INPUTS: classes, grades, credits, # of weighted classes, scanner
    //OUTPUT: double array grades with new percentage replacing old or with no replacement
    public static double[] imagAssign(String[] classes, double[] grades, int[] credits, int weightGPA, Scanner scan) {

        int userReq = 0;
        double currentGrade;
        double weight = 0;
        String grade;
        String yn;
        double gradeD = 0; 
        double finalPercent = 0;

        System.out.println("Which of the following class grades would you like to add an assignment to?");
        for (int i = 0; i < classes.length; i++) {
            System.out.println("[" + (i+1) + "] " + classes[i] + " - " + grades[i]);
        }
        userReq = Integer.parseInt(scan.nextLine());
        while (userReq < 1 || userReq > classes.length) {
            System.out.println("Select one of the classes please.");
            userReq = Integer.parseInt(scan.nextLine());
        }

        System.out.println("How much weight does this new assignment have as a PERCENT?");
        weight = Double.parseDouble(scan.nextLine());
        while (weight < 0 || weight >= 100) {
            System.out.println("Input a number in between 0 and 100");
            weight = Double.parseDouble(scan.nextLine());
        }

        System.out.println("What letter grade would you assign for this hypothetical assignment?");
        grade = scan.nextLine();
        while (grade.length() > 2) {
            System.out.println("Please put an appropriate grade.");
            grade = scan.nextLine();
        }
        if (grade.equals("A+")) {
            gradeD = 90;
        } else if (grade.equals("A")) {
            gradeD = 80;
        } else if (grade.equals("B+")) {
            gradeD = 70;
        } else if (grade.equals("B")) {
            gradeD = 60;
        } else if (grade.equals("C+")) {
            gradeD = 50;
        } else if (grade.equals("C")) {
            gradeD = 40;
        } else if (grade.equals("D+")) {
            gradeD = 30;
        } else if (grade.equals("D")) {
            gradeD = 20;
        } else {
            gradeD = 10;
        }

        finalPercent = (weight/100) * gradeD + (1-(weight)/100) * grades[userReq - 1];
        currentGrade = grades[userReq - 1];

        System.out.println("The hypothetical percent after adding this assignment is " + (Math.floor(finalPercent * 100) / 100) + ".");
        System.out.println("Your weighted GPA after adding this assignment is " + (Math.floor(checkGPA(grades, credits, true, weightGPA) * 100) / 100));
        System.out.println("Would you like to replace this percent with your current percentage? (y/n)");
        yn = scan.nextLine();
        while (yn.length() > 1) {
            System.out.println("Please put an appropriate grade.");
            yn = scan.nextLine();
        }

        if (yn.equals("y")) grades[userReq - 1] = finalPercent;

        return grades;

    }

    //method that gives user a study plan to dedicate time to classes in order to improve GPA
    //INPUTS: classes, grades, credits, scanner
    //OUTPUTS: None, everything printed out to user
    public static void optimizeTime(String[] classes, double[] grades, int[] credits, Scanner scan) {
        
        double freeTime = 0;
        double weightingSum = 0;
        double temp;
        double[] weightings = new double[classes.length];
        
        //assigns a numerical value to how easy it would be to get a grade to the next level
        for (int i = 0; i < classes.length; i++) {
            temp = grades[i];
            weightings[i] = (temp % 10 - 5);
            if (weightings[i] < 0) weightings[i] += 10;
            weightings[i] *= (10 * credits[i]);
            if ((classes[i].toUpperCase().indexOf("AP ") != -1) || (classes[i].toUpperCase().indexOf("AT ") != -1)) {
                weightings[i] += 10;
            }

            System.out.println(weightings[i]);
            weightings[i] *= (-10 * Math.sqrt(1 - Math.pow((temp-90), 2)/8100) + 10);
            
            System.out.println(weightings[i]);
            weightingSum += weightings[i];
            
        }

        System.out.println("How much free time do you have to work with? (in hours)");
        freeTime = Double.parseDouble(scan.nextLine());

        for (int i = 0; i < weightings.length; i++) {
            if (weightingSum == 0) {
                weightings[i] = freeTime/classes.length;
            } else {
                weightings[i] /= weightingSum;
            }
        }
        //  + Math.floor((freeTime * weightings[i]) * 100) / 100)
        for (int i = 0; i < classes.length; i++) {
            System.out.println("The recommended allocation of time is as follows:");
            System.out.println("[" + i + "] " + classes[i] + " - " + (Math.floor((freeTime * weightings[i]) * 100) / 100) + " hours.");
        }

    }

}