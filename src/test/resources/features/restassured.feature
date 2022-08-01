Feature: Check Add, Subtract, Divide, Multiply with soap using http://www.dneonline.com/calculator.asmx

  Scenario: Add
  Testing Add method
    When User Add number 8 with number 2
    Then Result has to be mathematically correct

  Scenario: Subtract
  Testing Subtract method
    When User Subtract number 8 with number 3
    Then Result has to be mathematically correct

  Scenario: Multiply
  Testing Multiply method
    When User Multiply number 6 with number 2
    Then Result has to be mathematically correct

  Scenario: Divide
  Testing Divide method
    When User Divide number 8 with number 2
    Then Result has to be mathematically correct




