package Tic_Tac_Ultimate;

public class Password{
    private char[] alpha;
    private char[] alphaCap ;
    private char[] num;
    private char[] special;
    private int complexity;
    private int minLength;
    private int maxLength;
    public Password(){
        alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        alphaCap = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        num = "0123456789".toCharArray();
        special = "~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?".toCharArray();
        this.complexity = 1;
        this.minLength = 8;
        this.maxLength = 16;
    }
    public Password(int complexity){
        this();
        this.complexity = complexity;
    }
    public Password(int complexity, int minLength, int maxLength){
        this(complexity);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
    public Password( int minLength, int maxLength){
        this();
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String generate(){
        int size = (int) ((Math.random()*9)+8);
        return generate(size);
    }
    public String generate(int size){
        String password = "";
        for(int i=0; i<size; i++){
            char[] generator;
            int array = (int) (Math.random()*(complexity+1));
            generator = switch(array){
                case 1 -> alphaCap;
                case 2 -> num;
                case 3 -> special;
                default -> alpha;
            };
            int index = (int) (Math.random()*generator.length);
            password = password.concat(Character.toString(generator[index]));
        }
        return password;
    }
    public String generate(int size,int complexity){
        this.complexity = complexity;
        return generate(size);
    }
    public void setComplexity(int complexity){
        if(complexity >= 1 && complexity <= 3)
            this.complexity = complexity;
    }
    public int check(String password){
        if (password.length() < minLength || password.length() > maxLength)//Normally recommended length of password is 8-16!
            return -10;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpperCase = true;
            else if (Character.isLowerCase(c))
                hasLowerCase = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else if (isSpecialCharacter(c))
                hasSpecial = true;
        }
        if(hasUpperCase && hasLowerCase && hasDigit && hasSpecial)//All inlcude, Password is very strong.
            return 2;
        else if(hasUpperCase && hasLowerCase && !(hasDigit || hasSpecial))//No number and special character, Password is medium! \nPlease use numbers and special char in combination with alphabets to make password stronger.
            return 0;
        else if(!(hasUpperCase || hasLowerCase) && hasDigit && hasSpecial)//No alphabets, Password is strong! \nPlease use alphabets in combination with numbers and special.
            return 1;
        else if(hasUpperCase && hasLowerCase)//alphabets and (number or special character), Password is weak! \nPassword can be made stronger by using captilization, numbers and special characters all in combination.
            return 1;
        else if((hasUpperCase || hasLowerCase) && hasDigit && hasSpecial)
            return 1;
        else if((hasUpperCase || hasLowerCase) && (hasDigit || hasSpecial))
            return -1;
        else//No number or special character and either lower or capital alpha, Password is very weak! \nPlease use numbers and Digits in combination with alphabets to make password stronger.
            return -2;
    }
    private boolean isSpecialCharacter(char c){
        String specialCharacters = "~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?";
        return specialCharacters.contains(Character.toString(c));
    }
}