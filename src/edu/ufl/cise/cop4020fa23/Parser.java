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
import edu.ufl.cise.cop4020fa23.exceptions.SyntaxException;

import static edu.ufl.cise.cop4020fa23.Kind.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser implements IParser {

		/*=== CLASS VARIABLES ===*/
		private List<Token> tokens = new ArrayList<>();
		private int current = 0;
		public Token currentToken;
		public Token t;
		Lexer lexer;

		//Constructor
		/*public Parser(List<Token> tokens) throws LexicalException{
			super();
			this.tokens = tokens;
			//this.lexer.tokens = tokens;
			currentToken = tokens.get(0);
			t = tokens.get(0);
		}

		 */
		public Parser(ILexer lexer) throws LexicalException {
			super();
			this.lexer = (Lexer) lexer;
			this.tokens = this.lexer.getTokens();
			currentToken = tokens.get(0);
			t = tokens.get(0);
		}

/*	public Parser(ILexer lexer) throws LexicalException {
		super();
		this.lexer = lexer;
	}
 */


	@Override
	public AST parse() throws PLCCompilerException {
		AST e = program();
		return e;
	}

	private AST program() throws PLCCompilerException {

		//Declare Program Const. Parameters
		Token firstToken = currentToken;
		Type returnType;
		String name;
		List<NameDef> params = new java.util.ArrayList<>();
		List<Block.BlockElem> blockELEM = new java.util.ArrayList<>();

		if (isKind(currentToken.kind()) || isType(currentToken.kind())){
			returnType = Type.kind2type(currentToken.kind());
			consume();
			Token identToken = match(IDENT);

			if (identToken != null){
				name = identToken.text();

				Token lParen = match(LPAREN);
				if (lParen == null){
					throw new SyntaxException("");
				}

				NameDef nameDefHelp = nameDef();

				if(nameDefHelp != null){
					params.add(nameDefHelp);
					while(isKind(COMMA)){
						consume();
						nameDefHelp = nameDef();
						if(nameDefHelp !=  null){
							params.add(nameDefHelp);
						}
						else{
							throw new SyntaxException("");
						}
					}
				}

				Token rParen = match(RPAREN);
				if (rParen == null){
					throw new SyntaxException("");
				}

				Declaration dec = declaration();
				Statement state = statement();

				while (dec != null || state != null){
					if(dec != null){
						blockELEM.add(dec);
					}
					else{
						blockELEM.add(state);
					}
					if(!isKind(SEMI)){
						throw new SyntaxException("");
					}
					consume();
					dec = declaration();
					state = statement();
				}

				if (!isKind(EOF)){
					throw new SyntaxException("");
				}

				Token typeT = new Token(currentToken.kind(), currentToken.pos, currentToken.length, currentToken.source, currentToken.sourceLocation());
				Block block = new Block(firstToken, blockELEM);
				return new Program(firstToken, typeT, identToken, params, block);
			}
		}
	//	throw new SyntaxException("");
		throw new UnsupportedOperationException();
	}


	/*=== HELPER FUNCTIONS ===*/
		private boolean check(Kind type){
			if (isAtEnd()) return false;
			return peek().kind() == type;
		}

		// Consume advances to the next token
		private Token consume() throws LexicalException{
			if (!isAtEnd()){
				lexer.next();
				current++;
				currentToken = tokens.get(current);
			}
			return previous();
		}

		private Token match(Kind... types) throws LexicalException{
			for (Kind type : types){
				if (check(type)){
					return consume();
				}
			}
			return null;
		}

		private boolean isAtEnd(){
			return peek().kind() == EOF;
		}

		private Token peek(){
			return tokens.get(current);
		}

		private Token previous(){
			return tokens.get(current - 1);
		}

		protected boolean isKind(Kind kind){
			return currentToken.kind() == kind;
		}

		protected boolean isType(Kind kind){ //current token's kind is a type
			try {
                return Type.kind2type(kind) == Type.kind2type(currentToken.kind());
			}
			catch(UnsupportedOperationException ignored){
			}
			return false;
		}

		protected boolean isKind(Kind... kinds){
			for (Kind k: kinds){
				if (k== currentToken.kind()){
					return true;
				}
			}
			return false;
		}

		/*=== GRAMMAR RULE FUNCTIONS ===*/

		public NameDef nameDef() throws SyntaxException, LexicalException {
			Token firstToken = currentToken;
			try {
				if (isKind(firstToken.kind())) {
					consume();
					Token name = match(IDENT);
					if (name != null) {
						return new NameDef(firstToken, name, new Dimension(firstToken, new IdentExpr(firstToken), new IdentExpr(firstToken)), name);
					} else {
						Dimension dim = dimension();
						if (dim != null) {
							name = match(IDENT);
							if (name != null) {
								return new NameDef(firstToken, name, dim, name);
							}
						} else {
							throw new SyntaxException();
						}
					}
				}
			}
			catch(LexicalException lexec){
				throw new LexicalException();
			}
			return null;
		}

		public Declaration declaration() throws PLCCompilerException{
			Token firstToken = currentToken;
			NameDef nameDefHelp = nameDef();
			if (nameDefHelp != null){
				if(isKind(ASSIGN) || isKind(LARROW)){
					Token op = currentToken;
					consume();
					Expr e = expr();
					return new Declaration(op, nameDefHelp, e); // used to be firstToken at op in this line

				}
				return new Declaration(firstToken, nameDefHelp, null);
			}
			return null;
		}

		public Expr expr() throws LexicalException{
			Expr e;
			if (isKind(RES_if) || isKind(RES_else)){
				e = conditionExpr();
			}
			else{
				e = logicalOrExpr();
			}
			if(match(ERROR) != null){
				throw new LexicalException("");
			}
			return e;
		}

		public Expr conditionExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr e;
			consume();
			match(LPAREN);
			Expr condition = expr();
			match(RPAREN);
			Expr trueCase = expr();
			match(RES_else);
			e = new ConditionalExpr(firstToken, condition, trueCase, expr());
			if(match(RES_fi) == null){
				throw new LexicalException();
			}
			return e;
		}

		public Expr logicalOrExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr left;
			Expr right;
			left = logicalAndExpr();
			while(isKind(OR)){
				Token op = currentToken;
				consume();
				right = logicalAndExpr();
				left = new BinaryExpr(firstToken, left, op, right);
			}
			return left;
		}

		public Expr logicalAndExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr left;
			Expr right;
			left = comparisonExpr();
			while(isKind(AND)){
				Token op = currentToken;
				consume();
				right = comparisonExpr();
				left = new BinaryExpr(firstToken, left, op, right);
			}
			return left;
		}

		public Expr comparisonExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr left;
			Expr right;
			left = additiveExpr();
			while(isKind(LT) || isKind(GT) || isKind(EQ) || isKind(NOT) || isKind(LE) || isKind(GE)){
				Token op = currentToken;
				consume();
				right = additiveExpr();
				left = new BinaryExpr(firstToken, left, op, right);
			}
			return left;
		}

		public Expr additiveExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr left;
			Expr right;
			left = multiplicativeExpr();
			while(isKind(PLUS) || isKind(MINUS)){
				Token op = currentToken;
				consume();
				right = multiplicativeExpr();
				left = new BinaryExpr(firstToken, left, op, right);
			}
			return left;
		}

		public Expr multiplicativeExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr left;
			Expr right;
			left = unaryExpr();
			while(isKind(TIMES) || isKind(DIV) || isKind(MOD)){
				Token op = currentToken;
				consume();
				right = unaryExpr();
				left = new BinaryExpr(firstToken, left, op, right);
			}
			return left;
		}


		public Expr unaryExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr e;
			if (isKind(BANG) || isKind(MINUS) || isKind(CONST) || isKind(RES_height) || isKind(RES_width)){
				Token op = currentToken;
				consume();
				Expr unaryExpr = unaryExpr();
				e = new UnaryExpr(firstToken, op, unaryExpr);
			}
			else{
				e = UnaryExprPostfix();
			}
			return e;
		}


		public Expr UnaryExprPostfix() throws LexicalException{
			Token firstToken = currentToken;
			Expr e = primaryExpr();
			PixelSelector pixelSelector = pixelSelector();
			if(pixelSelector != null){
				e = new PostfixExpr(firstToken, e, pixelSelector, new ChannelSelector(firstToken, firstToken)); // fix
			}
			return e;
		}


		public Expr primaryExpr() throws LexicalException{
			Token firstToken = currentToken;
			Expr e;
			if (isKind(BOOLEAN_LIT)){
				e = new BooleanLitExpr(firstToken);
				consume();
			}
			else if (isKind(STRING_LIT)){
				e = new StringLitExpr(firstToken);
				consume();
			}
			else if (isKind(NUM_LIT)){
				e = new NumLitExpr(firstToken);
				consume();
			}
			else if (isKind(FLOAT_LIT)){
				e = new NumLitExpr(firstToken);
				consume();
			}
			else if (isKind(IDENT)){
				e = new IdentExpr(firstToken);
				consume();
			}
			else if (isKind(LPAREN)){
				consume();
				e = expr();
				if(match(RPAREN) == null){
					throw new LexicalException();
				}
			}
			else if (isKind(CONST)){
				e = new ConstExpr(firstToken);
				consume();
			}
			else if (isKind(LANGLE)){
				consume();
				Expr red = expr();
				match(COMMA);
				Expr green = expr();
				match(COMMA);
				Expr blue = expr();
				match(RANGLE);
				e = new ConstExpr(firstToken);
			}
			/*else if (isKind(KW_CONSOLE)){  // FIX
				e = new ConsoleExpr(firstToken);
				consume();
			}*/
			else{
				if(!isAtEnd()){
					consume();
				}
				throw new LexicalException();
			}
			return e;
		}

		public PixelSelector pixelSelector() throws LexicalException{
			if(isKind(LSQUARE)){
				Token firstToken = currentToken;
				consume();
				Expr x = expr();
				match(COMMA);
				Expr y = expr();
				match(RSQUARE);
				return new PixelSelector(firstToken, x, y);
			}
			return null;
		}

		public Dimension dimension() throws SyntaxException, LexicalException {
			try {
				if (isKind(LSQUARE)) {
					Token firstToken = currentToken;
					consume();
					Expr width = expr();
					match(COMMA);
					Expr height = expr();
					match(RSQUARE);
					Dimension to_ret = new Dimension(firstToken, width, height);
					System.out.println(to_ret.toString());
					return to_ret;
				}
				else{
					throw new SyntaxException();
				}
			}
			catch(LexicalException lexec){
				throw new LexicalException();
			}
			catch(SyntaxException synex){
				System.out.println("ITS HERE");
				throw new SyntaxException();
			}
		}

		public Statement statement() throws LexicalException{
			Token firstToken = currentToken;
			Expr e;
			Token name = match(IDENT);
			if(name != null){
				PixelSelector pixelSelector = pixelSelector();
				if(match(ASSIGN) != null){
					e = expr();
					LValue tempL = new LValue(firstToken, name, pixelSelector(), null);
					return new AssignmentStatement(firstToken, tempL, e);
				}
				/*else if (match(LARROW) != null){		// fix
					e = expr();
					return new ReadStatement(firstToken, name.text(), pixelSelector, e);
				}*/
				else{
					return null;
				}
			}
			else{
				if(match(RES_write) != null){
					Expr source = expr();
					if(match(RARROW) != null){
						Expr dest = expr();
						return new WriteStatement(firstToken, source); // dest not included now
					}
					else{
						return null;
					}
				}
				else{
					if(match(RETURN) != null){
						e = expr();
						return new ReturnStatement(firstToken, e);
					}
					else{
						return null;
					}
				}
			}
		}

}
