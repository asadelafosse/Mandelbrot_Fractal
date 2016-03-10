package Mandelbrot;

/**
 * Created by Sebastien on 4-3-2016.
 */
public class ComplexNumber {

    final double real;
    final double imaginary;

    ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber square(ComplexNumber c){
        return new ComplexNumber(((c.real * c.real) - (c.imaginary * c.imaginary)),  (2 * c.real * c.imaginary));
    }

    ComplexNumber add(ComplexNumber c){
        return new ComplexNumber((this.real + c.real), (this.imaginary + c.imaginary));
    }
}
