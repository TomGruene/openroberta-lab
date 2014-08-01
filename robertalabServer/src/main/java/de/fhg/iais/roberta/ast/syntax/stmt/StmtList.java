package de.fhg.iais.roberta.ast.syntax.stmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fhg.iais.roberta.ast.syntax.Phrase;
import de.fhg.iais.roberta.codegen.lejos.Visitor;
import de.fhg.iais.roberta.dbc.Assert;

/**
 * This class allows to create list of {@link Stmt} elements.
 * Initially object from this class is writable. After adding all the elements to the list call {@link #setReadOnly()}.
 */
public class StmtList extends Stmt {
    private final List<Stmt> sl = new ArrayList<Stmt>();

    private StmtList() {
        super(Phrase.Kind.STMT_LIST);
    }

    /**
     * @return writable object of type {@link StmtList}.
     */
    public static StmtList make() {
        return new StmtList();
    }

    /**
     * Add new element to the list.
     * 
     * @param stmt
     */
    public final void addStmt(Stmt stmt) {
        Assert.isTrue(mayChange() && stmt != null && stmt.isReadOnly());
        this.sl.add(stmt);
    }

    /**
     * @return list with elements of type {@link Stmt}.
     */
    public final List<Stmt> get() {
        Assert.isTrue(isReadOnly());
        return Collections.unmodifiableList(this.sl);
    }

    @Override
    public void generateJava(StringBuilder sb, int indentation) {
        for ( Stmt stmt : this.sl ) {
            stmt.generateJava(sb, indentation);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for ( Stmt stmt : this.sl ) {
            sb.append(stmt.toString());
        }
        return sb.toString();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
