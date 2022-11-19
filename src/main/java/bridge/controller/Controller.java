package bridge.controller;

import bridge.Bridge;
import bridge.BridgeRandomNumberGenerator;
import bridge.domain.BridgeGame;
import bridge.domain.BridgeMaker;
import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
    private BridgeMaker bridgeMaker  = new BridgeMaker(bridgeRandomNumberGenerator);
    private BridgeGame bridgeGame = new BridgeGame();

    private Bridge bridge;
    private OutputView outputView;

    public void game() throws IllegalArgumentException{
        System.out.println("Please input length of bridge");
        int size = InputView.readBridgeSize();

        List<String> bridgeRoads = bridgeMaker.makeBridge(size);
        bridge = new Bridge(bridgeRoads);
        outputView = new OutputView(bridge);
        String retryChoice = null;
        int tryNumbers = 0;
        boolean pass = false;

        List<String> choices = new ArrayList<>();

        do {
            tryNumbers++;
            for (int i = 0 ; i< size; i++) {
                outputView.clearBuffer();
                System.out.println("choice where to move");
                String choice = InputView.readMoving();
                boolean moveIsValid = bridgeGame.move(bridge, choice, i);
                choices.add(choice);

                /* 출력 */
                outputView.printMap(i, choices);

                if (!moveIsValid) {
                    choices.clear();
                    System.out.println("please decide to try again");
                    retryChoice = InputView.readGameCommand();

                    break;
                }

                if (i==size-1) {
                    pass = true;
                    retryChoice = "Q";
                }
            }
        } while (retryChoice.equals("R"));

        /* 결과 출력 OutputView#printResult() */
        System.out.println("");
        outputView.printResult(pass, tryNumbers);
    }
}
