package me.allen.calculator.process;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class CalculateProcess {

    private StringBuilder operationString;

    private Expression finalExpression;

    public CalculateProcess() {
        this.operationString = new StringBuilder();
        this.finalExpression = null;
    }

    public CalculateProcess addCalculateType(CalculateType type) {
        String tempOperation = operationString.toString();
        if (tempOperation.isEmpty()) {
            if (type == CalculateType.SUBTRACT) this.operationString.append(type.getSymbol());
        } else {
            String lastOperator = String.valueOf(tempOperation.charAt(tempOperation.length() - 1));
            if (CalculateType.findBySymbol(lastOperator) != null) {
                CalculateType calculateType = CalculateType.findBySymbol(lastOperator);
                /*
                if (calculateType != type && calculateType.isDetectSpecialLastOperator() && type.isDetectSpecialLastOperator()) {
                    if (calculateType.getDetectOperatorIfPossible() != null && type.getDetectOperatorIfPossible() != null && type.getDetectOperatorIfPossible().equals(calculateType.getSymbol()))
                    this.operationString.append(type.getSymbol());
                }
                */
                if (calculateType != CalculateType.OPEN_BRACKET && type != CalculateType.CLOSE_BRACKET) {
                    if (calculateType.equals(CalculateType.CLOSE_BRACKET)) {
                        this.operationString.append(type.getSymbol());
                    } else {
                        if (type.equals(CalculateType.OPEN_BRACKET)) {
                            this.operationString.append(type.getSymbol());
                        }
                    }
                }

            } else {
                this.operationString.append(type.getSymbol());
            }
        }
        return this;
    }

    public CalculateProcess addWholeNumber(Integer number) {
        if (number != 0 && !this.operationString.toString().isEmpty()) {
            this.operationString.append(number);
        }
        return this;
    }

    public CalculateProcess addDot() {
        this.operationString.append(".");
        return this;
    }

    public CalculateProcess submit() {
        String operation = this.operationString.toString();
        operation = operation.replace("x", "*");
        try {
            this.finalExpression = new ExpressionBuilder(operation).build();
            return this;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public CalculateProcess clear() {
        this.operationString = new StringBuilder();
        this.finalExpression = null;
        return this;
    }

    @AllArgsConstructor
    @Getter
    public enum CalculateType {
        ADD("+", false, null),
        SUBTRACT("-", false, null),
        DIVIDE("/", false, null),
        MULTIPLY("x", false, null),
        OPEN_BRACKET("(", true, "("),
        CLOSE_BRACKET(")", true, ")");

        private String symbol;

        private boolean detectSpecialLastOperator;

        private String detectOperatorIfPossible;

        public static CalculateType findBySymbol(String symbol) {
            return Stream.of(CalculateType.values()).filter(type -> type.getSymbol().equals(symbol)).findFirst().orElse(null);
        }

    }
}
