<template>
  <v-snackbar
    v-model="snackbar"
    :timeout="120000"
    class="notificationParent"
    color="black">
    <v-card
      flat
      dark
      class="transparent">
      <template v-for="product in filteredProducts">
        <v-card-text
          :key="product.id"
          class="text-truncate notificationContent"
          dark>
          <template v-if="product.lastModifiedDate">
            {{ $t('exoplatform.perkstore.info.productModified', {0: product.title}) }}
          </template>
          <template v-else>
            {{ $t('exoplatform.perkstore.info.productCreated', {0: product.title}) }}
          </template>
        </v-card-text>
        <v-divider
          v-if="displayDivider"
          :key="product.id"
          dark />
      </template>
      <v-card-actions v-if="hasNewProducts">
        <v-spacer />
        <v-btn
          dark
          text
          :title="$t('exoplatform.perkstore.button.refresh')"
          @click="refreshList">
          {{ $t('exoplatform.perkstore.button.refresh') }}
        </v-btn>
        <v-spacer />
      </v-card-actions>
    </v-card>
    <v-btn
      :title="$t('exoplatform.perkstore.button.close')"
      dark
      text
      icon
      class="ml-0"
      @click="close">
      <v-icon dark>close</v-icon>
    </v-btn>
  </v-snackbar>
</template>
<script>
export default {
  props: {
    products: {
      type: Array,
      default: function() {
        return [];
      },
    },
  },
  data () {
    return {
      snackbar: false,
      snackbarDisplayed: [],
    }
  },
  computed: {
    displayDivider() {
      return this.products && this.products.length > 1;
    },
    hasNewProducts() {
      return this.products && this.products.find(product => !product.lastModifiedDate);
    },
    filteredProducts() {
      return this.products.filter(product => this.snackbarDisplayed.indexOf(product.id) < 0).slice(0, Math.min(3, this.products.length));
    },
  },
  watch: {
    snackbar() {
      if(!this.snackbar) {
        this.products.forEach(product => this.snackbarDisplayed.push(product.id));
      }
    },
    products() {
      this.snackbar = this.products.filter(product => this.snackbarDisplayed.indexOf(product.id) < 0).length;
    },
  },
  methods: {
    refreshList() {
      this.$emit('refresh-list');
    },
    close() {
      this.snackbar = false;
    }
  },
}
</script>
