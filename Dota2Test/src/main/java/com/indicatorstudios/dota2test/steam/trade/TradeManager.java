package com.indicatorstudios.dota2test.steam.trade;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.indicatorstudios.dota2test.steam.SteamService;

import uk.co.thomasc.steamkit.steam3.handlers.steamfriends.SteamFriends;
import uk.co.thomasc.steamkit.steam3.handlers.steamtrading.callbacks.SessionStartCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamtrading.callbacks.TradeProposedCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamtrading.callbacks.TradeResultCallback;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

public class TradeManager {
    public Trade currentTrade = null;
    public UserTradeListener listener = null;

    public View tradeStatus = null;
    public ImageButton yesButton;
    public ImageButton noButton;

    public SteamID pendingTradeRequest = null;
    public int tradeRequestID;
    public boolean tradeRequestSentByUs = false;

    public void trade(SteamID with) {
        /*if (pendingTradeRequest != null) {
            if (pendingTradeRequest.equals(with) && !tradeRequestSentByUs)
                activity().steamTrade.respondToTrade(tradeRequestID, true);
            if (!pendingTradeRequest.equals(with)) {
                activity().steamTrade.respondToTrade(tradeRequestID, false);
                activity().steamTrade.trade(with);
                pendingTradeRequest = with;
                tradeRequestSentByUs = true;
            }
        } else {
            activity().steamTrade.trade(with);
            pendingTradeRequest = with;
            tradeRequestSentByUs = true;
        }
        updateTradeStatus();*/
    }

    public void callbackSessionStart(SessionStartCallback obj) {
        Log.i("Trade", "Starting trade with " + obj.getOtherClient().convertToLong());
        SteamService.singleton.chat.appendToLog(obj.getOtherClient().convertToLong() + "", "<-- Trade Started -->");
        //MainActivity.instance.tracker().send(MapBuilder.createEvent("steam", "trade_start", "", null).build());

        SteamID myID = SteamService.singleton.steamClient.getSteamId();
        String sessionID = SteamService.singleton.sessionID;
        String token = SteamService.singleton.token;
        currentTrade = new Trade(myID, obj.getOtherClient(), sessionID, token, listener = new UserTradeListener());
        currentTrade.start();

        pendingTradeRequest = null;
        //updateTradeStatus();
        // browseToFragment(new FragmentTrade(), true);
    }

    public void callbackTradeProposed(TradeProposedCallback obj) {
        if (currentTrade != null || pendingTradeRequest != null) {
            //activity().steamTrade.respondToTrade(obj.getTradeID(), false);
            return;
        }
        pendingTradeRequest = obj.getOtherClient();
        tradeRequestID = obj.getTradeID();
        tradeRequestSentByUs = false;
        //updateTradeStatus();
    }

    public void callbackTradeResult(TradeResultCallback obj) {
        if (obj == null || obj.getResponse() == null) {
            Log.e("Steam", "Unexpected null TradeResultCallback...");
            return;
        }
        String name = SteamService.singleton.steamClient.getHandler(SteamFriends.class).getFriendPersonaName(obj.getOtherClient());
        /*switch (obj.getResponse()) {
            case Accepted:
                Toast.makeText(activity(), String.format(activity().getString(R.string.trade_result_accepted), name), Toast.LENGTH_LONG).show();
                break;
            case TargetAlreadyTrading:
                Toast.makeText(activity(), String.format(activity().getString(R.string.trade_result_alreadytrading), name), Toast.LENGTH_LONG).show();
                pendingTradeRequest = null;
                break;
            case Timeout:
                Toast.makeText(activity(), String.format(activity().getString(R.string.trade_result_timeout), name), Toast.LENGTH_LONG).show();
                pendingTradeRequest = null;
                break;
            case Declined:
                Toast.makeText(activity(), String.format(activity().getString(R.string.trade_result_declined), name), Toast.LENGTH_LONG).show();
                pendingTradeRequest = null;
                break;
            case Cancel:
                Toast.makeText(activity(), "Cancelled", Toast.LENGTH_LONG).show();
                pendingTradeRequest = null;
                break;
            default:
                // otherwise unable to trade.
                Toast.makeText(activity(), String.format(activity().getString(R.string.trade_result_unknown), name, obj.getResponse()), Toast.LENGTH_LONG).show();
                pendingTradeRequest = null;
                break;
        }
        updateTradeStatus();*/
    }

    public void cancelTrade() {
        if (currentTrade != null) {
            if (!currentTrade.die) {
                currentTrade.toRun.add(new Runnable() {
                    @Override
                    public void run() {
                        if (currentTrade == null)
                            return;
                        currentTrade.cancelTrade();
                        currentTrade = null;
                    }
                });
            }
        }
    }
}
