import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class FolhaDePagamentos {

    public static double calcularTotalPago(List<Funcionario<?>> funcionarios, LocalDate data) {
        return funcionarios.stream()
                .mapToDouble(f -> f.calcularSalario(data) + f.calcularBeneficio(data))
                .sum();
    }

    public static double calcularTotalSalarios(List<Funcionario<?>> funcionarios, LocalDate data) {
        return funcionarios.stream()
                .mapToDouble(f -> f.calcularSalario(data))
                .sum();
    }

    public static double calcularTotalBeneficios(List<Funcionario<?>> funcionarios, LocalDate data) {
        return funcionarios.stream()
                .mapToDouble(f -> f.calcularBeneficio(data))
                .sum();
    }

    public static Funcionario calcularFuncionarioComMaiorPagamento(List<Funcionario<?>> funcionarios, LocalDate data) {
        return funcionarios.stream()
                .max(Comparator.comparingDouble(f -> f.calcularSalario(data) + f.calcularBeneficio(data)))
                .orElse(null);
    }

    public static Funcionario calcularFuncionarioComMaiorBeneficio(List<Funcionario<?>> funcionarios, LocalDate data) {
        return funcionarios.stream()
                .filter(f -> f.calcularBeneficio(data) > 0)
                .max(Comparator.comparingDouble(f -> f.calcularBeneficio(data)))
                .orElse(null);
    }

    public static Funcionario<Vendedor> calcularVendedorComMaiorVenda(List<Funcionario<Vendedor>> vendedores, LocalDate data) {
        return vendedores.stream()
                .max(Comparator.comparingDouble(v -> ((Vendedor) v.getCargo()).getVendasNoMes(data)))
                .orElse(null);
    }



}
