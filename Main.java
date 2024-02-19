
class Number {
    private float real;
    private float imaginary;

    public Number(float input_real) {
        real = input_real;
        imaginary = 0;
    }

    public Number(float input_real, float input_imaginary) {
        real = input_real;
        imaginary = input_imaginary;
    }

    public Number add(Number number) {
        return new Number(real + number.real, imaginary + number.imaginary);
    }

    public Number sub(Number number) {
        return new Number(real - number.real, imaginary - number.imaginary);
    }

    public Number multi(Number number) {
        float new_real = real * number.real - imaginary * number.imaginary;
        float new_imaginary = real * number.imaginary + imaginary * number.real;
        return new Number(new_real, new_imaginary);
    }

    public String get_string() {
        if (imaginary > 0) {
            return real + " + " + imaginary + "i";
        } else if (imaginary < 0) {
            return real + " - " + imaginary * -1 + "i";
        } else {
            return real + "";
        }
    }
}

// Класс матрицы
class Matrix {
    private int rows;
    private int cols;
    private Number[][] data;
    

    public Matrix(int input_rows, int input_cols) {
        rows = input_rows;
        cols = input_cols;
        data = new Number[input_rows][input_cols];
    }

    public void set(int i, int j, Number number) {
        data[i][j] = number;
    }

    public Matrix add(Matrix matrix) {
        if (rows != matrix.rows || cols != matrix.cols) {
            System.out.println("Wrong sizes!");
            return null;
        } else {
            Matrix new_matrix = new Matrix(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    new_matrix.set(i, j, data[i][j].add(matrix.data[i][j]));
                }
            }
            System.out.println("Addiction result:");
            new_matrix.print();
            return new_matrix;
        }
    }

    public Matrix multi(Matrix matrix) {
        if (cols != matrix.rows) {
            System.out.println("Wrong sizes!");
            return null;
        } else {
            Matrix new_matrix = new Matrix(rows, matrix.cols);
            Number sum;
            Number mult;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < matrix.cols; j++) {
                    sum = new Number(0, 0);
                    for (int k = 0; k < cols; k++) {
                        mult = data[i][k].multi(matrix.data[k][j]);
                        sum = sum.add(mult);
                    }
                    new_matrix.set(i, j, sum);
                }
            }
            System.out.println("Multiplicate result:");
            new_matrix.print();
            return new_matrix;
        }
    }

    public Matrix transpose() {
        Matrix new_matrix = new Matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                new_matrix.set(j, i, this.data[i][j]);
            }
        }
        System.out.println("Transposition result:");
        new_matrix.print();
        return new_matrix;
    }

    public Number determinant() {
        if (rows != cols) {
            System.out.println("Wrong size!");
            return null;
        } else if (rows == 1) {
            return data[0][0];
        } else if (rows == 2) {
            Number pr1 = data[0][0].multi(data[1][1]);
            Number pr2 = data[1][0].multi(data[0][1]);
            return pr1.sub(pr2);
        } else {
            Number determinant = new Number(0);
            Matrix sub_matrix = new Matrix(rows - 1, cols - 1);
            boolean plus = true;
            for (int x = 0; x < rows; x++) {
                int subi = 0;
                for (int i = 1; i < rows; i++) {
                    int subj = 0;
                    for (int j = 0; j < rows; j++) {
                        if (j == x) {
                            continue;
                        }
                        sub_matrix.set(subi, subj, data[i][j]);
                        subj++;
                    }
                    subi++;
                }
                if (plus) {
                    determinant = determinant.add(data[0][x].multi(sub_matrix.determinant()));
                    plus = false;
                } else {
                    determinant = determinant.sub(data[0][x].multi(sub_matrix.determinant()));
                    plus = true;
                }
            }
            return determinant;
        }

    }

    public void print() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(data[i][j].get_string() + "; ");
            }
            System.out.println();
        }
    }

    ;

    public static void main(String[] args) {
        System.out.println("Real matrix:");
        Matrix real_matrix = new Matrix(4, 4);
        real_matrix.set(0, 0, new Number(9));
        real_matrix.set(0, 1, new Number(8));
        real_matrix.set(0, 2, new Number(7));
        real_matrix.set(0, 3, new Number(6));
        real_matrix.set(1, 0, new Number(5));
        real_matrix.set(1, 1, new Number(4));
        real_matrix.set(1, 2, new Number(3));
        real_matrix.set(1, 3, new Number(2));
        real_matrix.set(2, 0, new Number(1));
        real_matrix.set(2, 1, new Number(0));
        real_matrix.set(2, 2, new Number(1));
        real_matrix.set(2, 3, new Number(2));
        real_matrix.set(3, 0, new Number(3));
        real_matrix.set(3, 1, new Number(4));
        real_matrix.set(3, 2, new Number(5));
        real_matrix.set(3, 3, new Number(8));
        real_matrix.print();
        System.out.println("Real matrix determinant:");
        System.out.println(real_matrix.determinant().get_string());
        System.out.println();

        System.out.println("Complex matrix 1:");
        Matrix complex_matrix1 = new Matrix(2, 2);
        complex_matrix1.set(0, 0, new Number(1, -1));
        complex_matrix1.set(0, 1, new Number(0, 2));
        complex_matrix1.set(1, 0, new Number(1, 1));
        complex_matrix1.set(1, 1, new Number(3, -3));
        complex_matrix1.print();
        System.out.println("Complex matrix 1 determinant:");
        System.out.println(complex_matrix1.determinant().get_string());
        System.out.println();

        System.out.println("Complex matrix 2:");
        Matrix complex_matrix2 = new Matrix(2, 2);
        complex_matrix2.set(0, 0, new Number(1, 2));
        complex_matrix2.set(0, 1, new Number(3, 4));
        complex_matrix2.set(1, 0, new Number(5, 6));
        complex_matrix2.set(1, 1, new Number(7, 8));
        complex_matrix2.print();
        System.out.println("Complex matrix 2 determinant:");
        System.out.println(complex_matrix2.determinant().get_string());
        System.out.println();

        System.out.println("Complex matrix 1 add complex matrix 2:");
        complex_matrix1.add(complex_matrix2);
        System.out.println();

        System.out.println("Complex matrix 1 multiplicate complex matrix 2:");
        complex_matrix1.multi(complex_matrix2);
        System.out.println();

        System.out.println("Complex matrix 2 transposition:");
        complex_matrix2.transpose();
        System.out.println();
    }
}
