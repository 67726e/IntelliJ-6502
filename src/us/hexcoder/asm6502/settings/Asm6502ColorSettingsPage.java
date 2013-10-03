package us.hexcoder.asm6502.settings;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.hexcoder.asm6502.configuration.Asm6502Icons;
import us.hexcoder.asm6502.highlighter.Asm6502SyntaxHighlighter;
import us.hexcoder.asm6502.highlighter.Asm6502TextAttribute;

import javax.swing.*;
import java.io.*;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

/**
 * User: 67726e
 */

public class Asm6502ColorSettingsPage implements ColorSettingsPage {
	private static final String DEMO_URL = "/us/hexcoder/asm6502/resources/demo.6502";
	private static final String DEMO_TEXT;
	private static final AttributesDescriptor[] DESCRIPTORS;

	static {
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			inputStream = Asm6502ColorSettingsPage.class.getClassLoader().getResourceAsStream(DEMO_URL);
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			StringBuilder builder = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				builder.append(line).append(System.getProperty("line.separator"));
			}

			DEMO_TEXT = builder.toString();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load 6502 demo text", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("Unable to close 6502 demo text input stream", e);
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					throw new RuntimeException("Unable to close 6502 demo text input stream", e);
				}
			}
		}

		EnumSet<Asm6502TextAttribute> textAttributes = EnumSet.allOf(Asm6502TextAttribute.class);
		DESCRIPTORS = new AttributesDescriptor[textAttributes.size()];

		Iterator<Asm6502TextAttribute> iterator = textAttributes.iterator();

		for (int i = 0; iterator.hasNext(); i++) {
			Asm6502TextAttribute textAttribute = iterator.next();
			DESCRIPTORS[i] = new AttributesDescriptor(textAttribute.getName(), textAttribute.toKey());
		}
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return Asm6502Icons.ICON;
	}

	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new Asm6502SyntaxHighlighter();
	}

	@NotNull
	@Override
	public String getDemoText() {
		return DEMO_TEXT;
	}

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	@NotNull
	@Override
	public ColorDescriptor[] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "6502";
	}
}
