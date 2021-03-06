package br.com.cedran.coding.puzzle.usecase

import br.com.cedran.coding.puzzle.gateway.SaveGateway
import br.com.cedran.coding.puzzle.model.characters.Character
import br.com.cedran.coding.puzzle.model.characters.Warrior
import br.com.cedran.coding.puzzle.model.creatures.Dragon
import br.com.cedran.coding.puzzle.model.creatures.Monster
import br.com.cedran.coding.puzzle.usecase.battle.EndBattle

class EndBattleSpec extends BaseSpec {

    EndBattle endBattle
    Character character
    Monster monster
    SaveGateway saveGateway

    def setup() {
        character = new Warrior()
        monster = new Dragon()
        monster.lifeRemaining = 0l
        monster.drawing = ["dragon picture"]
        saveGateway = Mock(SaveGateway)
        endBattle = new EndBattle(screen, keyboard, character, monster, saveGateway)
        endBattle.message = ["Congrats!"]
    }

    def "End battle"() {
        given: "the player has just defeated a monster that gives 4 points of experience"
        monster.getExperience() >> 4l
        and: "the player has 2 points of experience"
        character.experience = 2l

        when: "the end battle is executed"
        Scenario scenario = endBattle.start()

        then: "the points of experience of the user is increased"
        character.experience == 6l
        and: "a congratulation is displayed"
        screenMessages[0] == "Congrats!"
        and: "a message saying which monster was killed and how many point of experience it gave is displayed"
        screenMessages[1] == "You just defeated a ${monster.getName()} and earned ${monster.getExperience()} points!"
        and: "the next scenario returned is explore"
        scenario instanceof Explore
    }


}
