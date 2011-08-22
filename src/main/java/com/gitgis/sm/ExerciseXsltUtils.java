/**
 * 
 */
package com.gitgis.sm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


/**
 * @author gg
 * 
 */
public class ExerciseXsltUtils {

	// private final static String BEGIN_TOKEN = "<span style=\"color: red\">";
	// private final static String END_TOKEN = "</span>";

	public static String questionRadio(NodeIterator iterator) {
		String retVal = "";
		Node node;
		Set<String> words = new HashSet<String>();
		while (null != (node = iterator.nextNode())) {
			words.add(node.getNodeValue());
		}
		retVal += "(";
		for (String word : words) {
			retVal += word + ", ";
		}
		if (retVal.length() > 2) {
			retVal = retVal.substring(0, retVal.length() - 2);
		}
		retVal += ")";

		return retVal;
	}

	public static String answerRadio(NodeIterator tagIterator,
			NodeIterator iterator, NodeIterator textIterator) {
		Node tagNode = tagIterator.getRoot();
		String retVal = "";

		int correct = -1;
		try {
			Node correctAttr = tagNode.getAttributes().getNamedItem("correct");
			if (correctAttr != null) {
				correct = Integer.valueOf(correctAttr.getNodeValue());
			}
		} catch (NumberFormatException skip) {
		}

		if (correct < 0) {
			int cnt = 1;
			Node node;
			while (null != (node = iterator.nextNode())) {
				if (node.getAttributes().getNamedItem("correct") != null) {
					correct = cnt;
					break;
				}
				cnt++;
			}
		}

		Node node;
		int cnt = 1;
		while (null != (node = textIterator.nextNode())) {
			if (correct == cnt) {
				retVal = node.getNodeValue();
				break;
			}
			cnt++;
		}

		return retVal;
	}

	public static String questionDragDrop(String text, NodeIterator iterator,
			int part) {
		String[] retVal = new String[3];

		Matcher matcher = Pattern.compile("(.*)\\[([0-9]+)\\](.*)").matcher(
				text);
		if (matcher.find()) {
			List<String> words = new ArrayList<String>();
			Node node;
			while (null != (node = iterator.nextNode())) {
				words.add(node.getNodeValue());
			}
			Collections.shuffle(words);

			String result = "(";
			for (String word : words) {
				result += word + ", ";
			}
			if (result.length() > 2) {
				result = result.substring(0, result.length() - 2);
			}
			result += ")";

			retVal[0] = matcher.group(1);
			retVal[1] = result;
			retVal[2] = matcher.group(3);
		}

		return retVal[part];
	}

	public static String answerDragDrop(String text, NodeIterator iterator,
			int part) {
		//String[] retVal = new String[3];

		// List<String> options = new ArrayList<String>();

		Node node;
		int cnt = 0;
		while (null != (node = iterator.nextNode())) {
			// options.add(node.getNodeValue());
			text = text.replace("[" + cnt + "]", node.getNodeValue());
			cnt++;
		}

		// int pos = -1;
		// Matcher matcher = Pattern.compile("(.*)\\[([0-9]+)\\](.*)").matcher(
		// text);
		// if (matcher.find()) {
		// try {
		// pos = Integer.valueOf(matcher.group(2));
		// } catch (NumberFormatException ignore) {
		// }
		//
		// String middleText = "";
		// middleText = node.getNodeValue();
		//
		// retVal[0] = matcher.group(1);
		// retVal[1] = middleText;
		// retVal[2] = matcher.group(3);
		// }
		return text;
	}

	public static String questionOrdering(NodeIterator iterator) {
		String retVal = "";
		Node node;
		List<String> words = new ArrayList<String>();
		while (null != (node = iterator.nextNode())) {
			words.add(node.getNodeValue());
		}
		retVal += "<";

		Collections.shuffle(words);

		for (String word : words) {
			retVal += word + ", ";
		}
		if (retVal.length() > 2) {
			retVal = retVal.substring(0, retVal.length() - 2);
		}
		retVal += ">";

		return retVal;
	}

	public static String answerOrdering(NodeIterator iterator) {
		String retVal = "";
		Node node;
		List<String> words = new ArrayList<String>();
		while (null != (node = iterator.nextNode())) {
			words.add(node.getNodeValue());
		}
		retVal += "<";
		for (String word : words) {
			retVal += word + " ";
		}
		if (retVal.length() > 2) {
			retVal = retVal.substring(0, retVal.length() - 1);
		}
		retVal += ">";

		return retVal;
	}

	public static String questionSelect(NodeIterator iterator) {
		String retVal = "";
		Node node;
		List<String> words = new ArrayList<String>();
		while (null != (node = iterator.nextNode())) {
			words.add(node.getNodeValue());
		}
		retVal += "[";
		for (String word : words) {
			retVal += word + ", ";
		}
		if (retVal.length() > 2) {
			retVal = retVal.substring(0, retVal.length() - 2);
		}
		retVal += "]";

		return retVal;
	}

}
