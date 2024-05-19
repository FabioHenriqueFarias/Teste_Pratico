import java.time.LocalDate;

public class Funcionario<T extends Cargo> {
    private final String nome;
    private T cargo;

    Funcionario(String nome, T cargo){
        this.nome = nome;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public double calcularSalario(LocalDate data) {
        return cargo.calcularSalario(data);
    }

    public double calcularBeneficio(LocalDate data) {
        return cargo.calcularBeneficio(data);
    }
}
