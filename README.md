# RogueLike
[![Build Status](https://travis-ci.org/akhvorov/roguelike.svg?branch=dev-1)](https://travis-ci.org/akhvorov/roguelike)  

Игра **Roguelike**. Для курса по Software Design.

## Команда
Александр Хворов  
Антон Елисеев  
Влад Танков

## Общие сведения о системе
Разрабатываемая система - игра в жанре Roguelike. Система является одиночной 
игрой, подразумевается использование её одним игроком. Требованием к системе 
пользователя является наличие Java Virtual Machine версии не ниже 1.8. 

### Границы системы
Жанр подразумевает генерацию карты случайным образом, наличие врагов с 
несколькими стратегиями поведения и персонажа игрока, управляемого с 
клавиатуры. Режим игры - пошаговый, однопользовательский. При запуске 
можно выбрать загрузку сохранения или начало новой игры. При загрузке 
загружается всегда только последнее сохранение.

### Системные треборвания
Подразумевается наличие клавиатуры и устройства вывода. Необходимо наличие 
Java Virtual Machine версии не ниже 1.8.

### Карта, персонаж игрока и NPC
- Для каждого уровня генерируется карта с фиксированным количеством 
враждебных NPC. Символ `#` обозначает стену - область карты, через которую 
игрок пройти не может. Гарантируется, что есть путь от персонажа до фыхода 
из лабиринта. Цель игрока на каждом уровне - дойти до выхода из лабиринта.

- Игрок управляет персонажем с клавиатуры  
Персонаж облядает при этом следующими характеристиками
    - **Координаты** - позиция игрока на карте
    - **Health** - здоровье игрока, игра продолжается пока оно не равно 0
    - **Damage** - урон, который персонаж наносит враждебным NPC
    - **Armors** - броня персонажа, пока есть броня здоровье не тратится
    - **Level** - уровень героя
    - **Knives** - элемент инвнтаря, которым можно атакавать враждебных NPC
- После каждого хода игрока совершают ход NPC, в области видимости которых 
находится персонаж. Существуют следующие виды враждебных NPC
    - `C` - coward enemy, hp=5, dmg=2, стараются атаковать сзади
    - `B` - brave enemy, hp=5, dmg=1, нападают спереди
    - `O` - not acting strategy, hp=5, dmg=2 стоят на месте и 
    атакуют при столкновении


## Роли и случаи использования
### Описание
Предпологается один пользователь системы - человек, играющий в игру. 
Таким образом есть один **Actor**, который взаимодействует с системой и 
реализует ее различные случаи использования.

### Use-case diagram
[![use-case][Use_case_diagram]][Use_case_diagram_url]

## Архитектура приложения
Приложение состоит из глобальных компонет, которые отвечают за ввод вывод и 
саму игровую систему, при этом каждая из них включает обособленные компоненты, 
отвечающие за считывание ввода, вывода на экран, управления персонажем, 
обновление игрового мира на основе действия персонажа и генерацию карты, 
на основе текущего состояния мира.

### Диаграмма компонент
[![Component][Component_Diagram]][Component_Diagram_url]

### Диаграмма классов
Более детально архитектуру можно видеть на диаграмме классов  
**ссылка на Д Классов**
[![Class][UML_class_diagram]][UML_class_diagram_url]

## Последовательность актов системы и изменеие ее состояний
Последовательность актов систмы во времени зависит от основного актора, 
игрока. После каждого его действия игровой мир делает свой ход и 
отображает то, что произошло в резултате.  
Изменение во времени отображается на диаграмме последовательностей.
### Диаграмма последовательностей
[![seq][seq_diagram]][seq_diagram_url]

### Диаграмма конечных автоматов
Программа может находится в следующих состояниях: главное меню или основной 
режим уровня, в главном меню выбирается уровень из доступных, 
после, генерируется карта данного уровня. Персонаж делает ход, 
после чего он может проиграть, выиграть либо ни то ни другое. 
В первом случае программа переходит в состояние основного меню, 
в котором остаются все те же уровни, что и были. Во втором случае 
добавляются новые уровни, и в последнам случае состояние не меняется и 
программа в ожидании следующего хода.  
[![states][state_diagram]][state_diagram_url]


[Use_case_diagram]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/UseCaseDiagram.png
[Use_case_diagram_url]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/UseCaseDiagram.png

[Component_Diagram]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/ComponentDiagram.png
[Component_Diagram_url]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/ComponentDiagram.png

[UML_class_diagram]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/ClassDiagram.png
[UML_class_diagram_url]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/ClassDiagram.png

[seq_diagram]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/SequenceDiagram.png
[seq_diagram_url]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/SequenceDiagram.png

[state_diagram]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/StateDiagram.png
[state_diagram_url]: https://raw.githubusercontent.com/akhvorov/roguelike/dev-1/docs/design/StateDiagram.png






