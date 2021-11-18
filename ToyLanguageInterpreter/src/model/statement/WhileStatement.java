package model.statement;

import exceptions.InvalidTypeException;
import model.ADT.DictionaryInterface;
import model.ADT.StackInterface;
import model.ProgramState;
import model.expression.ExpressionInterface;
import model.value.BoolValue;
import model.value.ValueInterface;

public class WhileStatement implements StatementInterface {
    private final ExpressionInterface expression;
    private final StatementInterface statement;

    public WhileStatement(ExpressionInterface expression, StatementInterface statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        DictionaryInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface expressionValue = expression.evaluate(symbolTable, heap);
        if (!(expressionValue instanceof BoolValue))
            throw new InvalidTypeException("Conditional expression is not a boolean!");

        if (((BoolValue) expressionValue).getValue() == true) {
            stack.push(this);
            stack.push(statement);
        }

        return null;
    }

    @Override
    public String toString() {
        return "while (" + expression.toString() + ") { " + statement.toString() + " }";
    }
}
