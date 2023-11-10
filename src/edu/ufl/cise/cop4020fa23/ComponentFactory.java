/*Copyright 2023 by Beverly A Sanders
 * 
 * This code is provided for solely for use of students in COP4020 Programming Language Concepts at the 
 * University of Florida during the fall semester 2023 as part of the course project.  
 * 
 * No other use is authorized. 
 * 
 * This code may not be posted on a public web site either during or after the course.  
 */
package edu.ufl.cise.cop4020fa23;

import edu.ufl.cise.cop4020fa23.ast.*;
import edu.ufl.cise.cop4020fa23.exceptions.LexicalException;
import edu.ufl.cise.cop4020fa23.exceptions.PLCCompilerException;

/**
 * Factory class providing static methods to create and return various components of the compiler
 */
public class ComponentFactory {

		public static ILexer makeLexer(String input) {
			return new Lexer(input);
		}
		
		public static IParser makeExpressionParser(ILexer lexer) throws LexicalException {
			return new ExpressionParser(lexer);
		}
		
		public static IParser makeExpressionParser(String input) throws LexicalException {
			return new ExpressionParser(makeLexer(input));
		}
		
		public static IParser makeParser(String input) throws LexicalException {
			return new Parser(makeLexer(input));
		}
		
		public static IParser makeParser(ILexer lexer) throws LexicalException {
			return new Parser(lexer);
		}

		public static ASTVisitor makeTypeChecker(){
			return new ASTVisitor() {
				@Override
				public Object visitAssignmentStatement(AssignmentStatement assignmentStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBinaryExpr(BinaryExpr binaryExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBlock(Block block, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBlockStatement(StatementBlock statementBlock, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitChannelSelector(ChannelSelector channelSelector, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitConditionalExpr(ConditionalExpr conditionalExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDeclaration(Declaration declaration, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDimension(Dimension dimension, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDoStatement(DoStatement doStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitExpandedPixelExpr(ExpandedPixelExpr expandedPixelExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitGuardedBlock(GuardedBlock guardedBlock, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitIdentExpr(IdentExpr identExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitIfStatement(IfStatement ifStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitLValue(LValue lValue, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitNameDef(NameDef nameDef, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitNumLitExpr(NumLitExpr numLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitPixelSelector(PixelSelector pixelSelector, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitPostfixExpr(PostfixExpr postfixExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitProgram(Program program, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitReturnStatement(ReturnStatement returnStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitStringLitExpr(StringLitExpr stringLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitUnaryExpr(UnaryExpr unaryExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitWriteStatement(WriteStatement writeStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBooleanLitExpr(BooleanLitExpr booleanLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitConstExpr(ConstExpr constExpr, Object arg) throws PLCCompilerException {
					return null;
				}
			};
		}

		public static ASTVisitor makeCodeGenerator(){
			return new ASTVisitor() {
				@Override
				public Object visitAssignmentStatement(AssignmentStatement assignmentStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBinaryExpr(BinaryExpr binaryExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBlock(Block block, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBlockStatement(StatementBlock statementBlock, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitChannelSelector(ChannelSelector channelSelector, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitConditionalExpr(ConditionalExpr conditionalExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDeclaration(Declaration declaration, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDimension(Dimension dimension, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitDoStatement(DoStatement doStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitExpandedPixelExpr(ExpandedPixelExpr expandedPixelExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitGuardedBlock(GuardedBlock guardedBlock, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitIdentExpr(IdentExpr identExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitIfStatement(IfStatement ifStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitLValue(LValue lValue, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitNameDef(NameDef nameDef, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitNumLitExpr(NumLitExpr numLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitPixelSelector(PixelSelector pixelSelector, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitPostfixExpr(PostfixExpr postfixExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitProgram(Program program, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitReturnStatement(ReturnStatement returnStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitStringLitExpr(StringLitExpr stringLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitUnaryExpr(UnaryExpr unaryExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitWriteStatement(WriteStatement writeStatement, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitBooleanLitExpr(BooleanLitExpr booleanLitExpr, Object arg) throws PLCCompilerException {
					return null;
				}

				@Override
				public Object visitConstExpr(ConstExpr constExpr, Object arg) throws PLCCompilerException {
					return null;
				}
			};
		}
		
}
