package pl.gozdzikowski.pawel.adventofcode.day17;

import java.util.Arrays;

import static pl.gozdzikowski.pawel.adventofcode.day17.ChronospatialComputer.OperandType.COMBO;
import static pl.gozdzikowski.pawel.adventofcode.day17.ChronospatialComputer.OperandType.LITERAL;

public class ChronospatialComputer {

    public State displayOutput(String commands, int aReg, int bReg, int cReg) {
        State currentState = new State(aReg, bReg, cReg, 0, "");
        int[] commandsArray = Arrays.stream(commands.split(",")).mapToInt(Integer::parseInt).toArray();
        while (currentState.instructionPointer + 1 < commandsArray.length) {

            int instruction  = commandsArray[currentState.instructionPointer];
            int operand = commandsArray[currentState.instructionPointer + 1];


            Operation operation = Operation.findOperationByOpcode(instruction);

            switch (operation.operandType) {
                case COMBO: {
                    if (operand >= 0 && operand <= 3) {
                        currentState = operation.execute(currentState, operand);
                    } else if (operand == 4) {
                        currentState = operation.execute(currentState, currentState.regA);
                    } else if (operand == 5) {
                        currentState = operation.execute(currentState, currentState.regB);
                    } else if (operand == 6) {
                        currentState = operation.execute(currentState, currentState.regC);
                    }
                }
                break;
                case LITERAL: {
                    currentState = operation.execute(currentState, operand);
                }
                break;
            }

        }

        return currentState;
    }

    record State(
            int regA,
            int regB,
            int regC,
            int instructionPointer,
            String output
    ) {

    }

    enum Operation {
        adv(0, COMBO) {
            @Override
            public State execute(State state, Integer operand) {
                int newValueOfA = state.regA / (int) Math.pow(2, operand);
                return new State(newValueOfA, state.regB, state.regC, state.instructionPointer + 2, state.output);
            }
        },
        bxl(1, LITERAL) {
            @Override
            public State execute(State state, Integer operand) {
                int newBValue = state.regB ^ operand;
                return new State(state.regA, newBValue, state.regC, state.instructionPointer + 2, state.output);
            }
        },
        bst(2, COMBO) {
            @Override
            public State execute(State state, Integer operand) {
                int newBValue = operand % 8;
                return new State(state.regA, newBValue, state.regC, state.instructionPointer + 2, state.output);
            }
        },
        jnz(3, LITERAL) {
            @Override
            public State execute(State state, Integer operand) {
                if (state.regA == 0)
                    return new State(state.regA, state.regB, state.regC, state.instructionPointer + 2, state.output);

                return new State(state.regA, state.regB, state.regC, operand, state.output);
            }
        },
        bxc(4, LITERAL) {
            @Override
            public State execute(State state, Integer operand) {
                int newStateB = state.regB ^ state.regC;
                return new State(state.regA, newStateB, state.regC, state.instructionPointer + 2, state.output);
            }
        },
        out(5, COMBO) {
            @Override
            public State execute(State state, Integer operand) {
                int comboOperand = operand % 8;
                String output = state.output.isEmpty() ? String.valueOf(comboOperand) : state.output + "," + comboOperand;
                return new State(state.regA, state.regB, state.regC, state.instructionPointer + 2, output);
            }
        },
        bdv(6, COMBO) {
            @Override
            public State execute(State state, Integer operand) {
                int newValueOfB = state.regA / (int) Math.pow(2, operand);
                return new State(state.regA, newValueOfB, state.regC, state.instructionPointer + 2, state.output);
            }
        },
        cdv(7, COMBO) {
            @Override
            public State execute(State state, Integer operand) {
                int newValueOfC = state.regA / (int) Math.pow(2, operand);
                return new State(state.regA, state.regB, newValueOfC, state.instructionPointer + 2, state.output);
            }
        };

        private final int opcode;
        private final OperandType operandType;

        Operation(int opcode, OperandType operandType) {
            this.opcode = opcode;
            this.operandType = operandType;
        }

        public abstract State execute(State state, Integer operand);

        public static Operation findOperationByOpcode(Integer opcode) {
            return Arrays.stream(Operation.values()).filter(el -> el.opcode == opcode).findFirst().orElse(null);
        }
    }

    enum OperandType {
        LITERAL, COMBO
    }
}
