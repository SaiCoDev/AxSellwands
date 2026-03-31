package com.artillexstudios.axsellwands.hooks.currency;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class VaultHook implements CurrencyHook {
    private Economy econ = null;

    @Override
    public void setup() {
        econ = resolveEconomy();
    }

    @Override
    public double getBalance(@NotNull Player p) {
        final Economy economy = resolveEconomy();
        if (economy == null) return 0;
        return economy.getBalance(p);
    }

    @Override
    public void giveBalance(@NotNull Player p, double amount) {
        final Economy economy = resolveEconomy();
        if (economy == null) return;
        economy.depositPlayer(p, amount);
    }

    @Override
    public void takeBalance(@NotNull Player p, double amount) {
        final Economy economy = resolveEconomy();
        if (economy == null) return;
        economy.withdrawPlayer(p, amount);
    }

    @Override
    public boolean isAvailable() {
        return resolveEconomy() != null;
    }

    private Economy resolveEconomy() {
        if (econ != null) {
            return econ;
        }

        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }

        econ = rsp.getProvider();
        return econ;
    }
}
