package de.fhg.iais.roberta.ast.syntax.expr;

import de.fhg.iais.roberta.ast.syntax.Phrase;
import de.fhg.iais.roberta.codegen.lejos.Visitor;

/**
 * This class represents the <b>logic_boolean</b> block from Blockly into the AST (abstract syntax tree).
 * Object from this class will generate boolean constant.<br/>
 * <br>
 * The client must provide the value of the boolean constant. <br>
 * <br>
 * To create an instance from this class use the method {@link #make(boolean)}.<br>
 */
public class BoolConst extends Expr {
    private final boolean value;

    private BoolConst(boolean value) {
        super(Phrase.Kind.BOOL_CONST);
        this.value = value;
        setReadOnly();
    }

    /**
     * creates instance of {@link BoolConst}. This instance is read only and can not be modified.
     * 
     * @param value that the boolean constant will have
     * @return read only object of class {@link BoolConst}.
     */
    public static BoolConst make(boolean value) {
        return new BoolConst(value);
    }

    /**
     * @return the value of the boolean constant.
     */
    public boolean isValue() {
        return this.value;
    }

    @Override
    public int getPrecedence() {
        return 999;
    }

    @Override
    public Assoc getAssoc() {
        return Assoc.NONE;
    }

    @Override
    public String toString() {
        return "BoolConst [" + this.value + "]";
    }

    @Override
    public void generateJava(StringBuilder sb, int indentation) {
        sb.append(this.value);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
