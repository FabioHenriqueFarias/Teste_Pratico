import java.time.LocalDate;

public abstract class Cargo {
    protected double salarioBase;
    protected double beneficios;
    protected LocalDate dataContratacao;

    public abstract double calcularSalario(LocalDate data);

    public abstract double calcularBeneficio(LocalDate data);

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }
}
