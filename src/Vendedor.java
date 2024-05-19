import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Vendedor extends Cargo{

    private Map<LocalDate, Double> vendasPorMes;

    public Vendedor(LocalDate dataContratacao){
        this.salarioBase = 12000;
        this.vendasPorMes = new HashMap<>();
        this.dataContratacao = dataContratacao;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }


    public void registrarVenda(LocalDate mesAno, double valor) {
        vendasPorMes.put(mesAno, valor);
    }

    public double getVendasNoMes(LocalDate data) {
        return vendasPorMes.getOrDefault(data, 0.0);
    }

    @Override
    public double calcularSalario(LocalDate data) {
        int anosDeServico = data.getYear() - dataContratacao.getYear();
        return salarioBase + (1800.00 * anosDeServico);    }

    @Override
    public double calcularBeneficio(LocalDate data) {
        double vendasNoMes = vendasPorMes.getOrDefault(data, 0.0);
        return 0.3 * vendasNoMes;
    }


}
