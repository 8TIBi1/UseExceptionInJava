package UseException;

// google关键字 : JVM idiv
// 所查阅 JVM 文档网址: https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html
// 另： 此文件编码格式为 UTF-8，使用 javac 编译时会报错，应该另存为 ANSI 码格式后才能正常编译，之后再
//     使用 javap -c 反汇编 .class 文件即可看到字节码，并从中找到 idiv和ddiv。
public class ThrowDemo {

    public static void main(String[] args) {
        intDivide();
        doubleDivide();
    }

    static void intDivide() {

        try {
            int i = 1, j = 0, k;
            k = i / j;
        } catch (ArithmeticException e) {
            System.out.println(e);
        }

    }

    static void doubleDivide() {
        double d1 = 100, d2 = 0, result;
        result = d1 / d2;
        System.out.println("浮点数除以零：" + result);
    }
}

// 关于整型的分母为 0 抛出 ArithmeticException 而 double 类型正常输出 Infinity，
// 是因为 JVM 采用的指令不同。
// int 除法的指令是 idiv， 而 double 除法的指令是 ddiv。
// 查阅 JVM 文档，可以看到官方文档中对于二者的规定不同，从而导致的结果不同。

/*
    idiv

    Operation

        Divide int

    Format

        idiv

    Forms

       idiv = 108 (0x6c)

    Operand Stack

        ..., value1, value2 →

        ..., result

    Description

        Both value1 and value2 must be of type int. The values are popped from the operand stack. The int result is the value of the Java programming language expression value1 / value2. The result is pushed onto the operand stack.

        An int division rounds towards 0; that is, the quotient produced for int values in n/d is an int value q whose
 magnitude is as large as possible while satisfying |d · q| ≤ |n|. Moreover, q is positive when |n| ≥ |d| and n and d have the same sign, but q is negative when |n| ≥ |d| and n and d have opposite signs.

        There is one special case that does not satisfy this rule: if the dividend is the negative integer of largest
 possible magnitude for the int type, and the divisor is -1, then overflow occurs, and the result is equal to the dividend. Despite the overflow, no exception is thrown in this case.


    Run-time Exception

        If the value of the divisor in an int division is 0, idiv throws an ArithmeticException.

 */

/*
    ddiv


    Operation

        Divide double

    Format

        ddiv

    Forms

        ddiv = 111 (0x6f)

    Operand Stack

        ..., value1, value2 →

        ..., result

    Description

        Both value1 and value2 must be of type double. The values are popped from the operand stack and undergo value set conversion (§2.8.3), resulting in value1' and value2'. The double result is value1' / value2'. The result is pushed onto the operand stack.

        The result of a ddiv instruction is governed by the rules of IEEE arithmetic:

        If either value1' or value2' is NaN, the result is NaN.

        If neither value1' nor value2' is NaN, the sign of the result is positive if both values have the same sign, negative if the values have different signs.

        Division of an infinity by an infinity results in NaN.

        Division of an infinity by a finite value results in a signed infinity, with the sign-producing rule just given.

        Division of a finite value by an infinity results in a signed zero, with the sign-producing rule just given.

        Division of a zero by a zero results in NaN; division of zero by any other finite value results in a signed zero, with the sign-producing rule just given.

        Division of a nonzero finite value by a zero results in a signed infinity, with the sign-producing rule just given.

        In the remaining cases, where neither operand is an infinity, a zero, or NaN, the quotient is computed and rounded to the nearest double using IEEE 754 round to nearest mode. If the magnitude is too large to represent as a double, we say the operation overflows; the result is then an infinity of appropriate sign. If the magnitude is too small to represent as a double, we say the operation underflows; the result is then a zero of appropriate sign.

        The Java Virtual Machine requires support of gradual underflow as defined by IEEE 754. Despite the fact that overflow, underflow, division by zero, or loss of precision may occur, execution of a ddiv instruction never throws a run-time exception.

 */


