package us.hexcoder.asm6502.psi;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import us.hexcoder.asm6502.configuration.Asm6502FileType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * User: 67726e
 */

public abstract class AbstractPsiSearchUtil {

	protected static List<PsiElement> find(Project project, FileVisitor fileVisitor, ElementVisitor elementVisitor) {
		PsiManager psiManager = PsiManager.getInstance(project);
		List<PsiElement> elements = new LinkedList<PsiElement>();

		for (VirtualFile virtualFile : getSourceFiles(project)) {
			Asm6502File sourceFile = (Asm6502File) psiManager.findFile(virtualFile);
			VisitorControl control = fileVisitor.visit(sourceFile);

			switch (control) {
				case SKIP:
					// Don't inspect this file any further
					break;
				case ACCEPT:
				case DEEPER:
					// Inspect the contents of this file
					elements.addAll(find(sourceFile, elementVisitor));
					break;
				default:
					throw new IllegalStateException("Unable to handle VisitorControl: " + control);
			}
		}

		return elements;
	}

	protected static List<PsiElement> find(Project project, ElementVisitor elementVisitor) {
		return find(project, ALL_FILES, elementVisitor);
	}

	protected static List<PsiElement> find(PsiElement element, ElementVisitor elementVisitor) {
		List<PsiElement> elements = new LinkedList<PsiElement>();

		for (PsiElement child : element.getChildren()) {
			VisitorControl control = elementVisitor.visit(child);

			switch (control) {
				case SKIP:
					// We don't care about this element, or it's children
					break;
				case DEEPER:
					// Inspect the children's elements further
					elements.addAll(find(child, elementVisitor));
					break;
				case ACCEPT:
					// This element matches out search
					elements.add(child);
					break;
				default:
					throw new IllegalStateException("Unable to handle VisitorControl: " + control);
			}
		}

		return elements;
	}

	private static Collection<VirtualFile> getSourceFiles(Project project) {
		return FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, Asm6502FileType.INSTANCE,
				GlobalSearchScope.allScope(project));
	}

	private static final FileVisitor ALL_FILES = new FileVisitor() {
		@Override
		public VisitorControl visit(Asm6502File file) {
			return VisitorControl.ACCEPT;
		}
	};
}
