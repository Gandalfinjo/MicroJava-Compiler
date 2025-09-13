package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn, value);
	}
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" "									{ }
"\t"								{ }
"\r\n"								{ }
"\b"								{ }
"\f"								{ }

"program"							{ return new_symbol(sym.PROG, yytext()); }
"break"								{ return new_symbol(sym.BREAK, yytext()); }
"class"								{ return new_symbol(sym.CLASS, yytext()); }
"if"								{ return new_symbol(sym.IF, yytext()); }
"else"								{ return new_symbol(sym.ELSE, yytext()); }
"const"								{ return new_symbol(sym.CONST, yytext()); }
"new"								{ return new_symbol(sym.NEW, yytext()); }
"print"								{ return new_symbol(sym.PRINT, yytext()); }
"read"								{ return new_symbol(sym.READ, yytext()); }
"return"							{ return new_symbol(sym.RETURN, yytext()); }
"void"								{ return new_symbol(sym.VOID, yytext()); }
"extends"							{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"							{ return new_symbol(sym.CONTINUE, yytext()); }
"union"								{ return new_symbol(sym.UNION, yytext()); }
"do"								{ return new_symbol(sym.DO, yytext()); }
"while"								{ return new_symbol(sym.WHILE, yytext()); }
"map"								{ return new_symbol(sym.MAP, yytext()); }
"interface"							{ return new_symbol(sym.INTERFACE, yytext()); }

"+"									{ return new_symbol(sym.PLUS, yytext()); }
"-"									{ return new_symbol(sym.MINUS, yytext()); }
"*"									{ return new_symbol(sym.STAR, yytext()); }
"/"									{ return new_symbol(sym.SLASH, yytext()); }
"%"									{ return new_symbol(sym.PERCENTAGE, yytext()); }
"=="								{ return new_symbol(sym.EQUAL, yytext()); }
"!="								{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">"									{ return new_symbol(sym.HIGHER, yytext()); }
">="								{ return new_symbol(sym.HIGHER_EQUAL, yytext()); }
"<"									{ return new_symbol(sym.LOWER, yytext()); }
"<="								{ return new_symbol(sym.LOWER_EQUAL, yytext()); }
"&&"								{ return new_symbol(sym.AND, yytext()); }
"||"								{ return new_symbol(sym.OR, yytext()); }
"="									{ return new_symbol(sym.ASSIGN, yytext()); }
"++"								{ return new_symbol(sym.INCREMENT, yytext()); }
"--"								{ return new_symbol(sym.DECREMENT, yytext()); }
";"									{ return new_symbol(sym.SEMICOLON, yytext()); }
":"									{ return new_symbol(sym.COLON, yytext()); }
","									{ return new_symbol(sym.COMMA, yytext()); }
"."									{ return new_symbol(sym.DOT, yytext()); }
"("									{ return new_symbol(sym.OPEN_PARENTHESIS, yytext()); }
")"									{ return new_symbol(sym.CLOSED_PARENTHESIS, yytext()); }
"["									{ return new_symbol(sym.OPEN_BRACKET, yytext()); }
"]"									{ return new_symbol(sym.CLOSED_BRACKET, yytext()); }
"{"									{ return new_symbol(sym.OPEN_BRACE, yytext()); }
"}"									{ return new_symbol(sym.CLOSED_BRACE, yytext()); }

"//" 								{ yybegin(COMMENT); }
<COMMENT> .							{ yybegin(COMMENT); }
<COMMENT> "\r\n"					{ yybegin(YYINITIAL); }

'.'									{ return new_symbol(sym.CHAR_CONST, yytext().charAt(1)); }
"true"								{ return new_symbol(sym.BOOL_CONST, true); }
"false"								{ return new_symbol(sym.BOOL_CONST, false); }
[0-9]+								{ return new_symbol(sym.NUMBER_CONST, Integer.parseInt(yytext())); }
([a-z]|[A-Z])[a-zA-Z0-9_]*			{ return new_symbol(sym.IDENT, yytext()); }

.									{ System.err.println("Lexical error (" + yytext() + "), line: " + (yyline + 1) + ", column: " + yycolumn); }