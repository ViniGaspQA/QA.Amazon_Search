Feature: Menu de Navegação

  # Casos positivos
  Scenario: Links do menu
    Given o usuário acessa a página inicial
    When clica na opção do menu "Mais vendidos"
    Then o sistema deve direcionar corretamente para tela com produtos "Mais vendidos"

  Scenario: Retração do menu
    Given o usuário acessa a página inicial
    When clica no menu
    When clica fora do menu
    Then o menu deve retrair automaticamente

  Scenario: Responsividade
    Given o usuário acessa a página inicial com tela de resolução baixa 1024x768
    When clica no ícone do menu
    Then o menu deve expandir corretamente com todas as opções visíveis

  Scenario: Submenus
    Given o usuário acessa a página inicial
    When passa o mouse ou clica em uma categoria do menu principal
    Then os submenus devem ser exibidos corretamente

  Scenario: Exibição menu diferentes resoluções
    Given o usuário usa um dispositivo com resolução específica (desktop, tablet ou smartphone)
    When acessa o menu
    Then o layout e as opções devem ser exibidos de forma adequada à resolução

  # Casos negativos
  Scenario: Responsividade resolução incomum
    Given o usuário acessa a página inicial com tela de resolução incomum (320x480)
    When clica no ícone do menu
    Then o menu deve expandir corretamente com todas as opções visíveis

  Scenario: Cliques repetitivos no menu
    Given o usuário acessa a página inicial
    When abre e fecha o menu rapidamente por diversas vezes (+10)
    Then o sistema deve exibir o menu corretamente

  Scenario: Conteúdos extensos no submenu
    Given o usuário acessa a página inicial
    When acessa um submenu com muitas opções
    When tenta visualizar todas as opções
    Then o sistema deve permitir o scroll ou adaptação para mostrar todas as opções sem erros visualização

  Scenario: Inativar recursos navegador (JavaScript)
    Given o usuário acessa a página inicial
    When desabilita o JavaScript no navegador
    When tenta abrir o menu
    Then o sistema deve exibir uma mensagem informando que a funcionalidade requer JavaScript ativado

  Scenario: Menu não carregado (problema de conexão)
    Given o usuário acessa a página inicial com problemas de conexão
    When tenta abrir o menu
    Then o sistema deve exibir um aviso ou carregar uma versão simplificada do menu
