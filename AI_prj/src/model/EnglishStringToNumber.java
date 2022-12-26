package model;

public class EnglishStringToNumber {

	public int convert(String word) {
		int wordnum = 0;
		String[] arrinwords = word.split(" ");
		int arrinwordsLength = arrinwords.length;
		if ("zero".equals(word))
			return 0;
		if (arrinwordsLength == 1) {
			wordnum = wordnum + (wordToNumber(arrinwords[0]));
		}
		if (arrinwordsLength == 2) {
			wordnum = wordnum + (wordToNumber(arrinwords[1]) + wordToNumber(arrinwords[0]));
		}

		return wordnum;
	}

	private int wordToNumber(String word) {
		int num = 0;
		switch (word) {
		case "one":
			num = 1;
			break;
		case "two":
			num = 2;
			break;
		case "three":
			num = 3;
			break;
		case "four":
			num = 4;
			break;
		case "five":
			num = 5;
			break;
		case "six":
			num = 6;
			break;
		case "seven":
			num = 7;
			break;
		case "eight":
			num = 8;
			break;
		case "nine":
			num = 9;
			break;
		case "ten":
			num = 10;
			break;
		case "eleven":
			num = 11;
			break;
		case "twelve":
			num = 12;
			break;
		case "thirteen":
			num = 13;
			break;
		case "fourteen":
			num = 14;
			break;
		case "fifteen":
			num = 15;
			break;
		case "sixteen":
			num = 16;
			break;
		case "seventeen":
			num = 17;
			break;
		case "eighteen":
			num = 18;
			break;
		case "nineteen":
			num = 19;
			break;
		case "twenty":
			num = 20;
			break;
		}
		return num;
	}
}
