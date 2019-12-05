import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.ArrayList;

/**
 * A class representing an abstract arithmetic expression
 */
public abstract class Expression
{
   /**
    * Creates a tree from an expression in postfix notation
    * @param postfix an array of Strings representing a postfix arithmetic expression
    * @return a new Expression that represents postfix
    */
   public static Expression expressionFromPostfix(String[] postfix)
   {
      //////REPLACE WITH YOUR CODE
      Stack<Expression> s = new Stack<Expression>();
      for (int i = 0; i < postfix.length; i++) {
         String token = postfix[i];
         if (token.matches("^-?\\d+")) {
            s.push(new IntegerOperand(Integer.parseInt(token)));
         }
         else if (token.matches("[a-zA-Z]")) {
            s.push(new VariableOperand(token));
         }
         else if (token.matches("-|\\*|/|\\+")) {
            s.push(makeExpression(token, s));
         }
         else {
            throw new IllegalArgumentException("Arguments can only be integers, variables or mathematics operands");
         }
      }
      return s.peek();
   }

   private static Expression makeExpression(String op, Stack<Expression> s) {
      Expression right = s.pop();
      Expression left = s.pop();
      Expression e;
      if (op.equals("+")) {
         e = new SumExpression(left, right);
      }
      else if (op.equals("-")) {
         e = new DifferenceExpression(left, right);
      }
      else if (op.equals("*")) {
         e = new ProductExpression(left, right);
      }
      else {
         e = new QuotientExpression(left, right);
      }
      return e;
   }

   /**
    * Creates a tree from an expression in infix notation
    * @param infix an array of Strings representing a infix arithmetic expression
    * @return a new Expression that represents infix
    */
   public static Expression expressionFromInfix(String[] infix)
   {
      //////REPLACE WITH YOUR CODE
      ArrayList<String> operators = new ArrayList<>();
      operators.add("+");
      operators.add("-");
      operators.add("*");
      operators.add("/");
      Stack<Expression> operand = new Stack<Expression>();
      Stack<String> other = new Stack<String>();
      for (int i = 0; i < infix.length; i++) {
         String token = infix[i];
         if (token.matches("^-?\\d+$")) {
            operand.push(new IntegerOperand(Integer.parseInt(token)));
         }
         else if (token.matches("[a-zA-Z]")) {
            operand.push(new VariableOperand(token));
         }
         else if (token.equals("(")) {
            other.push(token);
         }
         else if (token.matches("-|\\*|/|\\+")) {
            int j = operators.indexOf(token);
            boolean stop = false;
            while (!stop) {
               if (!other.empty()) {
                  int k = operators.indexOf(other.peek());
                  if (k == -1 || j > k) {
                     other.push(token);
                     stop = true;
                  }
                  else {
                     String op = other.pop();
                     operand.push(makeExpression(op, operand));
                  }
               }
               else {
                  other.push(token);
                  stop = true;
               }
            }
         }
         else if (token.equals(")")) {
            boolean openFound = false;
            while (!openFound && !other.empty()) {
               String op = other.pop();
               if (op.equals("(")) {
                  openFound = true;
               }
               else {
                  operand.push(makeExpression(op, operand));
               }
            }
         }
         else {
            throw new IllegalArgumentException("Arguments can only be integers, variables or mathematics operands");
         }
      }
      while (!other.empty()) {
         String op = other.pop();
         operand.push(makeExpression(op, operand));
      }
      return operand.peek();
   }

   /**
    * @return a String that represents this expression in prefix notation.
    */
   public abstract String toPrefix();

   /**
    * @return a String that represents this expression in infix notation.
    */  
   public abstract String toInfix();

   /**
    * @return a String that represents this expression in postfix notation.
    */  
   public abstract String toPostfix();

   /**
    * @return a String that represents the expression in infix notation
    */
   @Override
   public String toString()
   {
      return toInfix();
   }
   
   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public abstract Expression simplify();

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public abstract int evaluate(HashMap<String, Integer> assignments);

   /**
    * @return a Set of the variables contained in this expression
    */
   public abstract Set<String> getVariables();

   @Override
   public abstract boolean equals(Object obj);

   /**
    * Prints the expression as a tree in DOT format for visualization
    * @param filename the name of the output file
    */
   public void drawExpression(String filename) throws IOException
   {
      BufferedWriter bw = null;
      FileWriter fw = new FileWriter(filename);
      bw = new BufferedWriter(fw);
      
      bw.write("graph Expression {\n");
      
      drawExprHelper(bw);
      
      bw.write("}\n");
      
      bw.close();
      fw.close();     
   }

   /**
    * Recursively prints the vertices and edges of the expression tree for visualization
    * @param bw the BufferedWriter to write to
    */
   protected abstract void drawExprHelper(BufferedWriter bw) throws IOException;
}

/**
 * A class representing an abstract operand
 */
abstract class Operand extends Expression
{
}

/**
 * A class representing an expression containing only a single integer value
 */
class IntegerOperand extends Operand
{
   protected int operand;

   /**
    * Create the expression
    * @param operand the integer value this expression represents
    */
   public IntegerOperand(int operand)
   {
      this.operand = operand;
   }

   /**
    * @return a String that represents this expression in prefix notation.
    */   
   public String toPrefix()
   {
      ///////REPLACE WITH YOUR CODE
      return "" + this.operand;
   }

   /**
    * @return a String that represents this expression in postfix notation.
    */  
   public String toPostfix()
   {
      ///////REPLACE WITH YOUR CODE
      return ""+ this.operand;
   }   

   /**
    * @return a String that represents the expression in infix notation
    */
   public String toInfix()
   {
      //////REPLACE WITH YOUR CODE
      return "" + this.operand;
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      return this;
   }   

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return this.operand;
   }

   /**
    * @return a Set of the variables contained in this expression
    */
   public Set<String> getVariables()
   {
      //////REPLACE WITH YOUR CODE
      return new TreeSet<String>();
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof IntegerOperand)) {
         return false;
      }
      IntegerOperand io = (IntegerOperand) obj;
      return io.operand == this.operand;
   }   

   /**
    * Recursively prints the vertices and edges of the expression tree for visualization
    * @param bw the BufferedWriter to write to
    */
   protected void drawExprHelper(BufferedWriter bw) throws IOException
   {
      bw.write("\tnode"+hashCode()+"[label="+operand+"];\n");
   }
}

/**
 * A class representing an expression containing only a single variable
 */
class VariableOperand extends Operand
{
   protected String variable;

   /**
    * Create the expression
    * @param variable the variable name contained with this expression
    */
   public VariableOperand(String variable)
   {
      this.variable = variable;
   }

   /**
    * @return a String that represents this expression in prefix notation.
    */   
   public String toPrefix()
   {
      ///////REPLACE WITH YOUR CODE
      return this.variable;
   }

   /**
    * @return a String that represents this expression in postfix notation.
    */  
   public String toPostfix()
   {
      ///////REPLACE WITH YOUR CODE
      return this.variable;
   }   

   /**
    * @return a String that represents the expression in infix notation
    */
   public String toInfix()
   {
      //////REPLACE WITH YOUR CODE
      return this.variable;
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      return this;
   }   

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return assignments.get(this.variable);
   }

   /**
    * @return a Set of the variables contained in this expression
    */
   public Set<String> getVariables()
   {
      //////REPLACE WITH YOUR CODE
      TreeSet<String> ts = new TreeSet<String>();
      ts.add(this.variable);
      return ts;
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof VariableOperand)) {
         return false;
      }
      VariableOperand vo = (VariableOperand) obj;
      return vo.variable.equals(this.variable);
   }   

   /**
    * Recursively prints the vertices and edges of the expression tree for visualization
    * @param bw the BufferedWriter to write to
    */
   protected void drawExprHelper(BufferedWriter bw) throws IOException
   {
      bw.write("\tnode"+hashCode()+"[label="+variable+"];\n");
   }   
}

/**
 * A class representing an expression involving an operator
 */
abstract class OperatorExpression extends Expression
{
   protected Expression left;
   protected Expression right;

   /**
    * Create the expression
    * @param left the expression representing the left operand
    * @param right the expression representing the right operand
    */
   public OperatorExpression(Expression left, Expression right)
   {
      this.left = left;
      this.right = right;
   }

   /**
    * @return a string representing the operator
    */
   protected abstract String getOperator();     
   
   /**
    * Recursively prints the vertices and edges of the expression tree for visualization
    * @param bw the BufferedWriter to write to
    */
   protected void drawExprHelper(BufferedWriter bw) throws IOException
   {
      String rootID = "\tnode"+hashCode();
      bw.write(rootID+"[label=\""+getOperator()+"\"];\n");

      bw.write(rootID + " -- node" + left.hashCode() + ";\n");
      bw.write(rootID + " -- node" + right.hashCode() + ";\n");
      left.drawExprHelper(bw);
      right.drawExprHelper(bw);
   }

   /**
    * @return a String that represents this expression in prefix notation.
    */
   public String toPrefix()
   {
      ///////REPLACE WITH YOUR CODE
      return this.getOperator() + " " + this.left.toPrefix() + " " + this.right.toPrefix();
   }

   /**
    * @return a String that represents this expression in postfix notation.
    */
   public String toPostfix()
   {
      ///////REPLACE WITH YOUR CODE
      return this.left.toPostfix() + " " + this.right.toPostfix() + " " + this.getOperator();
   }

   /**
    * @return a String that represents the expression in infix notation
    */
   public String toInfix()
   {
      //////REPLACE WITH YOUR CODE
      return "(" + this.left.toInfix() + " " + this.getOperator() + " " + this.right.toInfix() + ")";
   }

   /**
    * @return a Set of the variables contained in this expression
    */
   public Set<String> getVariables()
   {
      //////REPLACE WITH YOUR CODE
      TreeSet<String> ts = new TreeSet<String>();
      ts.addAll(this.left.getVariables());
      ts.addAll(this.right.getVariables());
      return ts;
   }

}

/**
 * A class representing an expression involving an sum
 */
class SumExpression extends OperatorExpression
{
   /**
    * Create the expression
    * @param left the expression representing the left operand
    * @param right the expression representing the right operand
    */
   public SumExpression(Expression left, Expression right)
   {
      super(left, right);
   }

   /**
    * @return a string representing the operand
    */
   protected String getOperator()
   {
      return "+";
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      Expression simL = this.left.simplify();
      Expression simR = this.right.simplify();
      if (simL instanceof IntegerOperand && simR instanceof IntegerOperand) {
         IntegerOperand left = (IntegerOperand) simL;
         IntegerOperand right = (IntegerOperand) simR;
         HashMap<String, Integer> hm = new HashMap<String, Integer>();
         return new IntegerOperand(left.evaluate(hm) + right.evaluate(hm));
      }
      if (simL.equals(new IntegerOperand(0))) {
         return simR;
      }
      if (simR.equals(new IntegerOperand(0))) {
         return simL;
      }
      return new SumExpression(simL, simR);
   }

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return this.left.evaluate(assignments) + this.right.evaluate(assignments);
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof SumExpression)) {
         return false;
      }
      SumExpression e = (SumExpression) obj;
      return e.left.equals(this.left) && e.right.equals(this.right) || e.left.equals(this.right) && e.right.equals(this.left);
   }   
}

/**
 * A class representing an expression involving an differnce
 */
class DifferenceExpression extends OperatorExpression
{
   /**
    * Create the expression
    * @param left the expression representing the left operand
    * @param right the expression representing the right operand
    */
   public DifferenceExpression(Expression left, Expression right)
   {
      super(left, right);
   }

   /**
    * @return a string representing the operand
    */
   protected String getOperator()
   {
      return "-";
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      Expression simL = this.left.simplify();
      Expression simR = this.right.simplify();
      if (simL instanceof IntegerOperand && simR instanceof IntegerOperand) {
         IntegerOperand left = (IntegerOperand) simL;
         IntegerOperand right = (IntegerOperand) simR;
         HashMap<String, Integer> hm = new HashMap<String, Integer>();
         return new IntegerOperand(left.evaluate(hm) - right.evaluate(hm));
      }
      if (simR.equals(new IntegerOperand(0))) {
         return simL;
      }
      else if (simL.equals(simR)){
         return new IntegerOperand(0);
      }
      return new DifferenceExpression(simL, simR);
   }

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return this.left.evaluate(assignments) - this.right.evaluate(assignments);
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof DifferenceExpression)) {
         return false;
      }
      DifferenceExpression e = (DifferenceExpression) obj;
      return e.left.equals(this.left) && e.right.equals(this.right);
   }      
}

/**
 * A class representing an expression involving a product
 */
class ProductExpression extends OperatorExpression
{
   /**
    * Create the expression
    * @param left the expression representing the left operand
    * @param right the expression representing the right operand
    */
   public ProductExpression(Expression left, Expression right)
   {
      super(left, right);
   }

   /**
    * @return a string representing the operand
    */
   protected String getOperator()
   {
      return "*";
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      Expression simL = this.left.simplify();
      Expression simR = this.right.simplify();
      if (simL instanceof IntegerOperand && simR instanceof IntegerOperand) {
         IntegerOperand left = (IntegerOperand) simL;
         IntegerOperand right = (IntegerOperand) simR;
         HashMap<String, Integer> hm = new HashMap<String, Integer>();
         return new IntegerOperand(left.evaluate(hm) * right.evaluate(hm));
      }
      if (simL.equals(new IntegerOperand(0)) || simR.equals(new IntegerOperand(0))) {
         return new IntegerOperand(0);
      }
      if (simL.equals(new IntegerOperand(1))) {
         return simR;
      }
      else if (simR.equals(new IntegerOperand(1))) {
         return simL;
      }
      return new ProductExpression(simL, simR);
   }   

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return this.left.evaluate(assignments) * this.right.evaluate(assignments);
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof ProductExpression)) {
         return false;
      }
      ProductExpression e = (ProductExpression) obj;
      return e.left.equals(this.left) && e.right.equals(this.right) || e.left.equals(this.right) && e.right.equals(this.left);
   }      
}

/**
 * A class representing an expression involving a division
 */
class QuotientExpression extends OperatorExpression
{
   /**
    * Create the expression
    * @param left the expression representing the left operand
    * @param right the expression representing the right operand
    */
   public QuotientExpression(Expression left, Expression right)
   {
      super(left, right);
   }

   /**
    * @return a string representing the operand
    */
   protected String getOperator()
   {
      return "/";
   }

   /**
    * @return a new Expression mathematically equivalent to this one, but simplified.
    */  
   public Expression simplify()
   {
      //////REPLACE WITH YOUR CODE
      Expression simL = this.left.simplify();
      Expression simR = this.right.simplify();
      if (simL instanceof IntegerOperand && simR instanceof IntegerOperand) {
         IntegerOperand left = (IntegerOperand) simL;
         IntegerOperand right = (IntegerOperand) simR;
         HashMap<String, Integer> hm = new HashMap<String, Integer>();
         return new IntegerOperand(left.evaluate(hm) / right.evaluate(hm));
      }
      if (simL.equals(new IntegerOperand(0)) || simR.equals(new IntegerOperand(1))) {
         return simL;
      }
      if (simL.equals(simR)) {
         return new IntegerOperand(1);
      }
      return new QuotientExpression(simL, simR);
   }

   /**
    * Evaluates the expression given assignments of values to variables.
    * @param assignments a HashMap from Strings (variable names) to Integers (values).
    * @return the numerica result of evaluating the expression with the given variable assignments
    */
   public int evaluate(HashMap<String, Integer> assignments)
   {
      //////REPLACE WITH YOUR CODE
      return this.left.evaluate(assignments) / this.right.evaluate(assignments);
   }

   /**
    * @param obj and Object to compare to
    * @return true if obj is a logically equivalent Expression 
    */
   @Override
   public boolean equals(Object obj)
   {
      /////REPLACE WITH YOUR CODE
      if (!(obj instanceof QuotientExpression)) {
         return false;
      }
      QuotientExpression e = (QuotientExpression) obj;
      return e.left.equals(this.left) && e.right.equals(this.right);
   }      
}
