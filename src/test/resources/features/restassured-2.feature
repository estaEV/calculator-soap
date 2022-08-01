Feature: Additional operations

  Scenario Outline: Simple calculation
  Calculations input parameters provided by datatable
    When Random user <operation> number <firstNumber> with number <secondNumber>
    Then Result has to be mathematically correct
    Examples:
      | firstNumber | secondNumber | operation |
      | 0           | 8            | Add       |
      | 3           | 9            | Multiply  |

  Scenario: Simple calculation 2
  Do calculation with nums form col1 and nums from col2
    When Random user do operation with the following values
      | col1 | col2 | operation |
      | 2    | 3    | Subtract       |
      | 4    | 7    | Subtract       |
      | 4    | 7    | Subtract       |
    Then Result has to be mathematically correct
