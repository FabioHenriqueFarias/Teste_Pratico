import java.time.LocalDate;

public class Gerente extends Cargo{

    public Gerente(LocalDate dataContratacao){
        this.salarioBase = 20000.00;
        this.beneficios = 0;
        this.dataContratacao = dataContratacao;
    }

    @Override
    public double calcularSalario(LocalDate data) {
        int anosDeServico = data.getYear() - dataContratacao.getYear();
        return salarioBase + (3000.00 * anosDeServico);
    }

    @Override
    public double calcularBeneficio(LocalDate data) {
        return this.beneficios * salarioBase;
    }
}
