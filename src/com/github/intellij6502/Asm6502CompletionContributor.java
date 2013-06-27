package com.github.intellij6502;

import com.github.intellij6502.psi.Asm6502Type;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * User: 67726e
 */

public class Asm6502CompletionContributor extends CompletionContributor {
	public Asm6502CompletionContributor() {
		// Add completion suggestions for the LABEL_ARGUMENT after a mnemonic
		extend(CompletionType.BASIC, PlatformPatterns.psiElement(Asm6502Type.LABEL_ARGUMENT)
				.withLanguage(Asm6502Language.INSTANCE),
				new CompletionProvider<CompletionParameters>() {
					@Override
					protected void addCompletions(@NotNull CompletionParameters completionParameters,
												  ProcessingContext processingContext,
												  @NotNull CompletionResultSet completionResultSet) {
						// TODO: Lookup all LABEL_DECLARATION elements and add to `completionResultSet`
					}
				});
	}
}
