import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static List<Funcionario<?>> funcionarios = new ArrayList<>();
    private static List<Funcionario<Vendedor>> vendedores = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void exibir() {
        System.out.println(
                "----------------------------------------------------------------\n" +
                        "1 - Cadastrar Funcionario\n" +
                        "2 - Adicionar venda para vendedor\n" +
                        "3 - Retornar valores a serem pagos para os Funcionarios no mês somente de Salário\n" +
                        "4 - Retornar valores a serem pagos para os Funcionarios no mês somente de Benefícios\n" +
                        "5 - Retornar o Funcionário que recebeu maior valor de Salário\n" +
                        "6 - Retornar o Funcionário que recebeu maior valor de Benefícios\n" +
                        "7 - Retornar o Vendedor que mais vendeu no mês\n" +
                        "8 - Retornar valores a serem pagos para os Funcionarios no mês\n" +
                        "9 - Sair\n" +
                        "----------------------------------------------------------------"
        );
    }

    public static void opcoes() {
        boolean running = true;

        while (running) {

            exibir();
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    adicionarVendaVendedor();
                    break;
                case 3:
                    retornarValoresPagosSalarios();
                    break;
                case 4:
                    retornarValoresPagosBeneficios();
                    break;
                case 5:
                    retornarFuncionarioMaiorSalario();
                    break;
                case 6:
                    retornarFuncionarioMaiorBeneficio();
                    break;
                case 7:
                    retornarVendedorMaisVendeu();
                    break;
                case 8:
                    retornarValoresPagos();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

    }

    private static void cadastrarFuncionario(){
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cargo (Secretario, Vendedor, Gerente): ");
        String cargoStr = scanner.nextLine();
        System.out.print("Data de contratação (MM/yyyy): ");
        String dataContratacaoStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataContratacao = LocalDate.parse("01/" + dataContratacaoStr, formatter);
        boolean EVendedor = false;

        Funcionario<?> funcionario = null;
        if (cargoStr.equalsIgnoreCase("Secretario")) {
            Secretario cargo = new Secretario(dataContratacao);
            funcionario = new Funcionario<Secretario>(nome, cargo);
        } else if (cargoStr.equalsIgnoreCase("Vendedor")) {
            Vendedor cargo = new Vendedor(dataContratacao);
            funcionario = new Funcionario<Vendedor>(nome, cargo);
            EVendedor = true;
        } else if (cargoStr.equalsIgnoreCase("Gerente")) {
            Gerente cargo = new Gerente(dataContratacao);
            funcionario = new Funcionario<Gerente>(nome, cargo);
        } else {
            System.out.println("Cargo inválido!");
            return;
        }

        if (EVendedor){
            vendedores.add((Funcionario<Vendedor>) funcionario);
        }

        if (funcionario != null) {
            funcionarios.add(funcionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        }
    }



    private static void adicionarVendaVendedor() {
        System.out.print("Nome do vendedor: ");
        String nomeVendedor = scanner.nextLine();

        Funcionario<Vendedor> vendedor = encontrarVendedor(nomeVendedor);
        if (vendedor == null) {
            System.out.println("Vendedor não encontrado!");
            return;
        }

        System.out.print("Data da venda (MM/yyyy): ");
        String dataVendaStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataVenda = LocalDate.parse("01/" + dataVendaStr, formatter);

        Vendedor cargoVendedor = (Vendedor) vendedor.getCargo();
        if (dataVenda.isBefore(cargoVendedor.getDataContratacao()) || dataVenda.isAfter(LocalDate.now())) {
            System.out.println("Data da venda inválida! Deve estar no período em que o vendedor está cadastrado.");
            return;
        }

        System.out.print("Valor da venda: ");
        double valorVenda = scanner.nextDouble();
        scanner.nextLine();

        cargoVendedor.registrarVenda(dataVenda, valorVenda);
        System.out.println("Venda registrada com sucesso!");
    }



    private static Funcionario<Vendedor> encontrarVendedor(String nome) {
        for (Funcionario<Vendedor> vendedor : vendedores) {
            if (vendedor.getNome().equalsIgnoreCase(nome)) {
                return vendedor;
            }
        }
        return null;
    }



    private static void retornarValoresPagos() {
        LocalDate data = solicitarData();
        double totalPago = FolhaDePagamentos.calcularTotalPago(funcionarios, data);
        System.out.println("Total pago: " + totalPago);
    }

    private static void retornarValoresPagosSalarios() {
        LocalDate data = solicitarData();
        double totalSalarios = FolhaDePagamentos.calcularTotalSalarios(funcionarios, data);
        System.out.println("Total pago em salários: " + totalSalarios);
    }

    private static void retornarValoresPagosBeneficios() {
        LocalDate data = solicitarData();
        double totalBeneficios = FolhaDePagamentos.calcularTotalBeneficios(funcionarios, data);
        System.out.println("Total pago em benefícios: " + totalBeneficios);
    }

    private static void retornarVendedorMaisVendeu() {
        LocalDate data = solicitarData();
        Funcionario<Vendedor> vendedor = FolhaDePagamentos.calcularVendedorComMaiorVenda(vendedores, data);
        if (vendedor != null) {
            Vendedor funcionarioVendedor = (Vendedor) vendedor.getCargo();
            System.out.println(vendedor.getNome() + " foi o vendedor que mais vendeu de: " + funcionarioVendedor.getVendasNoMes(data));
        } else {
            System.out.println("Nenhum vendedor encontrado.");
        }
    }

    private static void retornarFuncionarioMaiorBeneficio() {
        LocalDate data = solicitarData();
        Funcionario funcionario = FolhaDePagamentos.calcularFuncionarioComMaiorBeneficio(funcionarios, data);
        if (funcionario != null) {
            System.out.println("Funcionário com maior benefício: " + funcionario.getNome());
        } else {
            System.out.println("Nenhum funcionário encontrado.");
        }
    }

    private static void retornarFuncionarioMaiorSalario() {
        LocalDate data = solicitarData();
        Funcionario funcionario = FolhaDePagamentos.calcularFuncionarioComMaiorPagamento(funcionarios, data);
        if (funcionario != null) {
            System.out.println("Funcionário com maior salário: " + funcionario.getNome());
        } else {
            System.out.println("Nenhum funcionário encontrado.");
        }
    }

    private static LocalDate solicitarData() {
        System.out.print("Informe o mês e ano (MM/yyyy): ");
        String dataStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse("01/" + dataStr, formatter);
    }
}
