package com.rcx.funkyfluids.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ConsumingShapelessRecipe extends ShapelessRecipe {

	final String group;
	final ItemStack result;
	final NonNullList<Ingredient> ingredients;

	public ConsumingShapelessRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
		super(pId, pGroup, pResult, pIngredients);
		this.group = pGroup;
		this.result = pResult;
		this.ingredients = pIngredients;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer pContainer) {
		return NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);
	}

	public static class Serializer implements RecipeSerializer<ConsumingShapelessRecipe> {

		public ConsumingShapelessRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
			String s = GsonHelper.getAsString(pJson, "group", "");
			NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(pJson, "ingredients"));
			if (nonnulllist.isEmpty()) {
				throw new JsonParseException("No ingredients for shapeless recipe");
			} else if (nonnulllist.size() > 9) {
				throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is 9");
			} else {
				ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
				return new ConsumingShapelessRecipe(pRecipeId, s, itemstack, nonnulllist);
			}
		}

		private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();
			for(int i = 0; i < pIngredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
				nonnulllist.add(ingredient);
			}
			return nonnulllist;
		}

		public ConsumingShapelessRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
			String s = pBuffer.readUtf();
			int i = pBuffer.readVarInt();
			NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
			for(int j = 0; j < nonnulllist.size(); ++j) {
				nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
			}
			ItemStack itemstack = pBuffer.readItem();
			return new ConsumingShapelessRecipe(pRecipeId, s, itemstack, nonnulllist);
		}

		public void toNetwork(FriendlyByteBuf pBuffer, ConsumingShapelessRecipe pRecipe) {
			pBuffer.writeUtf(pRecipe.group);
			pBuffer.writeVarInt(pRecipe.ingredients.size());
			for(Ingredient ingredient : pRecipe.ingredients) {
				ingredient.toNetwork(pBuffer);
			}
			pBuffer.writeItem(pRecipe.result);
		}
	}
}
