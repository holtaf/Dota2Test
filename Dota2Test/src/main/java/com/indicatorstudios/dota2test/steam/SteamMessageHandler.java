package com.indicatorstudios.dota2test.steam;

import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

public interface SteamMessageHandler {
	public void handleSteamMessage(CallbackMsg msg);
}
