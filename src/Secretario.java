import java.time.LocalDate;

public class Secretario extends Cargo{

    public Secretario(LocalDate dataContratacao){
        this.salarioBase = 7000.00;
        this.beneficios = 0.2;
        this.dataContratacao = dataContratacao;
    }

    @Override
    public double calcularSalario(LocalDate data) {
        int anosDeServico = data.getYear() - dataContratacao.getYear();
        return salarioBase + (1000.00 * anosDeServico);    }

    @Override
    public double calcularBeneficio(LocalDate data) {
        return this.beneficios * calcularSalario(data);
    }
}
